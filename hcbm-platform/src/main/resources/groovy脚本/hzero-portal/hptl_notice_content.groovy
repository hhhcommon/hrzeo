package script.db

databaseChangeLog(logicalFilePath: 'script/db/hptl_notice_content.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2018-10-25-hptl_notice_content") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hptl_notice_content_s', startValue:"1")
        }
        createTable(tableName: "hptl_notice_content", remarks: "公告具体内容") {
        column(name: "notice_content_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
        column(name: "notice_id", type: "bigint(20)",  remarks: "公告头ID")  {constraints(nullable:"false")}  
        column(name: "notice_body", type: "longtext",  remarks: "公告内容")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID")  {constraints(nullable:"false")}  
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

    }
   createIndex(tableName: "hptl_notice_content", indexName: "hptl_notice_content_n1") {
            column(name: "notice_id")
            column(name: "tenant_id")
        }          

  }    
}