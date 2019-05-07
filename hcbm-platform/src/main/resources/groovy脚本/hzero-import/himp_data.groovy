package script.db

databaseChangeLog(logicalFilePath: 'script/db/himp_data.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-02-19-himp_data") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'himp_data_s', startValue:"1")
        }
        createTable(tableName: "himp_data", remarks: "合同扩展信息表") {
            column(name: "id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "batch", type: "varchar(" + 60 * weight + ")",  remarks: "批次")  {constraints(nullable:"false")}
            column(name: "template_code", type: "varchar(" + 30 * weight + ")",  remarks: "模板编码")  {constraints(nullable:"false")}  
            column(name: "data_status", type: "varchar(" + 30 * weight + ")",  remarks: "数据状态[NEW(Excel导入),VALID_SUCCESS(验证成功),VALID_FAILED(验证失败),IMPORT_SUCCESS(导入成功),IMPORT_FAILED(导入失败)]")  {constraints(nullable:"false")}  
            column(name: "sheet_index", type: "int(11)",  remarks: "页码")  {constraints(nullable:"false")}  
            column(name: "error_msg", type: "varchar(" + 255 * weight + ")",  remarks: "错误信息")   
            column(name: "data", type: "longtext",  remarks: "数据")   

        }
   createIndex(tableName: "himp_data", indexName: "idx_batch") {
            column(name: "batch")
        }
    }
}