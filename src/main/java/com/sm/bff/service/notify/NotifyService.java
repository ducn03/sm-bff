package com.sm.bff.service.notify;

import com.sm.bff.constant.RestConstant;
import com.sm.bff.service.notify.dto.NotifyAnalytics;
import com.sm.bff.service.notify.dto.NotifyDTO;
import com.sm.bff.service.notify.dto.NotifyUnread;
import com.sm.jpa.domain.Notify;
import com.sm.jpa.repository.NotifyRepository;
import com.sm.jpa.repository.UserRepository;
import com.sm.lib.dto.UserInfo;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotifyService {
    private final UserRepository userRepository;
    private final NotifyRepository notifyRepository;
    private final NotifyMapper notifyMapper;
    private final NotifyMachineService notifyMachineService;
    private final boolean READ = RestConstant.NOTIFY.READ;
    private final boolean UNREAD = RestConstant.NOTIFY.UNREAD;

    public NotifyService(UserRepository userRepository, NotifyRepository notifyRepository, NotifyMapper notifyMapper, NotifyMachineService notifyMachineService) {
        this.userRepository = userRepository;
        this.notifyRepository = notifyRepository;
        this.notifyMapper = notifyMapper;
        this.notifyMachineService = notifyMachineService;
    }

    /**
     * Lấy danh sách notify theo page
     * @param query
     * @return
     */
    public Uni<List<NotifyDTO>> getNotifyByUser(PanacheQuery<Notify> query){
        return query.list()
                .map(notifies -> notifies.stream()
                .map(this.notifyMapper::toDTO)
                .collect(Collectors.toList()));
    }

    public PanacheQuery<Notify> getQueryNotify(long userId,  int pageIndex, int pageSize){
        return notifyRepository.findQueryNotifyByUserId(userId, pageIndex, pageSize);
    }

    /**
     * Đánh dấu đã đọc tất cả thông báo
     * @param userInfo
     * @return
     */
    @Transactional
    public Uni<Boolean> markAllNotifyAsRead(UserInfo userInfo){
        return userRepository.findByUsername(userInfo.getUsername())
                .map(user -> user)
                .flatMap(user -> {
                    return notifyRepository.findByUserIdAndIsRead(user.getId(), UNREAD).map(notifies -> {
                        notifies.forEach(notify -> notify.setRead(READ));
                        return true;
                    });
                });
    }

    /**
     * Đánh dấu đã đọc thông báo
     * @param notifyId
     * @return
     */
    @Transactional
    public Uni<Boolean> markNotifyAsRead(long notifyId){
        return notifyRepository.findById(notifyId).map(notify -> {
            notify.setRead(READ);
            return true;
        });
    }

    /**
     * Đánh dấu đã đọc các thông báo
     * @param notifyIds
     * @return
     */
    @Transactional
    public Uni<Boolean> markNotifyAsRead(List<Long> notifyIds){
        return notifyRepository.findByIds(notifyIds).map(notifies -> {
            notifies.forEach(notify -> notify.setRead(READ));
            return true;
        });
    }

    /**
     * Đánh dấu chưa đọc các thông báo
     * @param notifyIds
     * @return
     */
    public Uni<Boolean> markNotifyAsUnread(List<Long> notifyIds){
        return notifyRepository.findByIds(notifyIds).map(notifies -> {
            notifies.forEach(notify -> notify.setRead(UNREAD));
            return true;
        });
    }

    /**
     * Số liệu tổng quan về thông báo
     */
    public Uni<NotifyAnalytics> getNotifyAnalytics(UserInfo userInfo){
        return userRepository.findByUsername(userInfo.getUsername())
                .map(user -> user)
                .flatMap(user -> {
                    return notifyRepository.findByUserId(user.getId())
                            .map(notifyMachineService::calculate);
                });
    }

    /**
     * Lấy count của thông báo chưa đọc
     */
    public Uni<NotifyUnread> countUnreadNotify(UserInfo userInfo){
        return userRepository.findByUsername(userInfo.getUsername())
                .map(user -> user)
                .flatMap(user -> {
                    return notifyRepository.countUnread(user.getId())
                            .map(unreadCount -> {
                                NotifyUnread notifyUnread = new NotifyUnread();
                                notifyUnread.setUnread(unreadCount);
                                return notifyUnread;
                            });
                });
    }

}
