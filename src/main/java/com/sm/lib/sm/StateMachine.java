package com.sm.lib.sm;

import com.sm.jpa.domain.SM;
import com.sm.jpa.repository.SMRepository;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.utils.AppThrower;
import io.smallrye.mutiny.Uni;

import java.sql.Timestamp;
import java.time.Instant;

public abstract class StateMachine<I extends SMInput, T extends SMData<T>> {

    private final SMRepository smRepository;

    protected StateMachine(SMRepository smRepository) {
        this.smRepository = smRepository;
    }

    protected abstract Template<T> init();

    public Template<T> getTemplate() {
        return init();
    }

    public abstract T loadData(I input, Template<T> template);

    public void saveData(T data, Template<T> template) {
        this.smRepository.persistAndFlush(data.getSm());
        saveDataInternal(data, template);
    }

    protected abstract void saveDataInternal(T data, Template<T> template);

    protected SM createNew(long masterId, Template<T> template) {
        State<T> state = template.getDefaultInitState();
        SM sm = new SM();
        sm.setMasterId(masterId);
        sm.setTemplateId(template.getId());
        sm.setStatus(state.getKey());
        sm.setActionStatus(state.getDefaultActionStatus());
        long epochSecond = Instant.now().getEpochSecond();
        sm.setUpdatedAt(new Timestamp(epochSecond * 1000));
        return (SM) smRepository.persistAndFlush(sm);
    }

    protected SM loadInternal(I input, Template<T> template) {
        Uni<SM> smOptional = smRepository.findByTemplateIdAndMasterIdOrderByIdDesc(template.getId(), input.getMasterId());
        if (smOptional == null) {
            AppThrower.ep(ErrorCodes.SYSTEM.SM.BAD_REQUEST_INPUT_NOT_FOUND);
        }
        return (SM) smOptional;
    }

    // on auto State Machine
//    public void handle(ITrigger<T> trigger) {
//        load();
//        T data = trigger.getData();
//        String stateKey = data.key();
//        State<T> state = this.template.getState(stateKey);
//        if (state == null) {
//            AppThrower.pe(ErrorCode.INTERNAL_ERROR.SM.BAD_REQUEST_STATE_NOT_FOUND);
//        }
//        String actionKey = data.action();
//        Action<T> action = state.getAction(actionKey);
//        if (action == null) {
//            AppThrower.pe(ErrorCode.INTERNAL_ERROR.SM.BAD_REQUEST_STATE_NOT_FOUND);
//        }
//        action.doAction(data);
//    }


}