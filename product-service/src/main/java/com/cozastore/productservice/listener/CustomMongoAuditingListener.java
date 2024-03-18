package com.cozastore.productservice.listener;

import com.cozastore.productservice.entity.AbstractAuditingEntity;
import com.cozastore.productservice.payload.ResponseToken;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;

@Component
public class CustomMongoAuditingListener extends AbstractMongoEventListener<Object> {


    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object entity = event.getSource();
        if (entity instanceof AbstractAuditingEntity abstractAuditingEntity) {
            String currentUser = "Anonymous";
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null
                    && requestAttributes.getRequest().getAttribute("user") != null) {
                currentUser = ((ResponseToken) requestAttributes.getRequest()
                        .getAttribute("user")).getEmail();
            }
            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            if (abstractAuditingEntity.getCreatedDate() == null) {
                abstractAuditingEntity.setCreatedDate(currentDate);
                abstractAuditingEntity.setCreatedBy(currentUser);
            }
            abstractAuditingEntity.setLastModifiedDate(currentDate);
            abstractAuditingEntity.setLastModifiedBy(currentUser);
        }
    }
}
