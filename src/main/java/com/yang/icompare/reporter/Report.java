package com.yang.icompare.reporter;

import java.util.List;

import com.yang.icompare.core.Result;

public interface Report {
    void generateReports(List<Result> result);
}
