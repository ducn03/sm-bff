package com.sm.lib.sm;

import com.sm.jpa.domain.SM;
import lombok.Data;

@Data
public abstract class SMData<T extends SMData<T>> {

    private SM sm;
    public abstract Template<T> getTemplate();

}
