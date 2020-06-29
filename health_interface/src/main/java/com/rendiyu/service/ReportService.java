package com.rendiyu.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface ReportService {


    Map<String, Object> getBusinessReportData() throws Exception;
}
