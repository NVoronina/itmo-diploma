package com.medical.medonline.converter;

import com.medical.medonline.dto.response.ManagerResponse;
import com.medical.medonline.entity.ManagerEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class CustomConverter {
    private final ModelMapper mm;

    public CustomConverter(ModelMapper mm) {
        this.mm = mm;
    }

    public Converter<ManagerEntity, ManagerResponse> getManagerConverter() {
        return new Converter<ManagerEntity, ManagerResponse>() {
            @Override
            public ManagerResponse convert(MappingContext<ManagerEntity, ManagerResponse> context) {
                ManagerResponse ce = mm.map(context.getSource().getUser(), ManagerResponse.class);
                ce.setId(context.getSource().getId());
                return ce;
            }
        };
    }

}
