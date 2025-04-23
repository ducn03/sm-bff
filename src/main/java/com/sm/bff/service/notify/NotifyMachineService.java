package com.sm.bff.service.notify;

import com.sm.bff.service.notify.dto.NotifyAnalytics;
import com.sm.jpa.domain.Notify;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class NotifyMachineService {
    public NotifyAnalytics calculate(List<Notify> notifies){
        NotifyAnalytics notifyAnalytics = new NotifyAnalytics();
        if (notifies == null || notifies.isEmpty()) {
            return notifyAnalytics;
        }

        long read = 0;
        long unread = 0;
        for (Notify notify : notifies){
            if (notify.isRead) read++;
            unread++;
        }

        notifyAnalytics.setRead(read);
        notifyAnalytics.setUnread(unread);
        return notifyAnalytics;
    }
}
