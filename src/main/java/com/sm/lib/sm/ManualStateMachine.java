package com.sm.lib.sm;

import com.sm.jpa.domain.SM;
import com.sm.jpa.repository.SMRepository;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.utils.AppThrower;

public abstract class ManualStateMachine<I extends SMInput, T extends SMData<T>> extends StateMachine<I, T> {

    protected ManualStateMachine(SMRepository smRepository) {
        super(smRepository);
    }

    protected void handle(I input, String act, ActionCallback<T> callback) {
        Template<T> template = getTemplate();
        T data = loadData(input, template);
        SM sm = data.getSm();
        State<T> state = template.getState(sm.getStatus());
        if (state == null) {
            AppThrower.ep(ErrorCodes.SYSTEM.SM.BAD_REQUEST_STATE_NOT_FOUND);
        }
        Action<T> action = state.getAction(act);
        if (action == null) {
            AppThrower.ep(ErrorCodes.SYSTEM.SM.BAD_REQUEST_ACTION_NOT_FOUND);
        }
        sm.setStatus(action.next().getKey());
        try {
            callback.doBack(data);
        } finally {
            saveData(data, template);
        }
    }
}
