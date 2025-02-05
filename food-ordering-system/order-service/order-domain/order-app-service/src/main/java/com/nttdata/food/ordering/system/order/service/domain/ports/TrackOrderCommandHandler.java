package com.nttdata.food.ordering.system.order.service.domain.ports;

import com.nttdata.food.ordering.system.order.service.domain.payload.track.TrackOrderQuery;
import com.nttdata.food.ordering.system.order.service.domain.payload.track.TrackOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrackOrderCommandHandler {
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
    
}
