package com.Berries.Wang.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: DynamicDataSource
 * @Description: '摘要'
 * @Author: 'Wei.Wang'
 * @Date: 2025/2/26 21:39
 **/
public class DynamicDataSource  extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "default";
    }
}
