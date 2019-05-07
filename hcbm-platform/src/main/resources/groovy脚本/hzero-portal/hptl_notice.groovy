package script.db

databaseChangeLog(logicalFilePath: 'script/db/hptl_notice.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2018-10-25-hptl_notice") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hptl_notice_s', startValue:"1")
        }
        createTable(tableName: "hptl_notice", remarks: "公告基础信息") {
        column(name: "notice_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
        column(name: "lang", type: "varchar(30)",  remarks: "语言code")  {constraints(nullable:"false")}  
        column(name: "title", type: "varchar(300)",  remarks: "公告主题")  {constraints(nullable:"false")}  
        column(name: "receiver_type_code", type: "varchar(60)",  remarks: "接收方类型,值集：HPTL.NOTICE.RECERVER_TYPE")  {constraints(nullable:"false")}  
        column(name: "notice_category_code", type: "varchar(60)",  remarks: "公告类别,值集：HPTL.NOTICE.NOTICE_CATEGORY")  {constraints(nullable:"false")}  
        column(name: "notice_type_code", type: "varchar(60)",  remarks: "公告类型,值集：HPTL.NOTICE.NOTICE_TYPE")  {constraints(nullable:"false")}  
        column(name: "attachment_uuid", type: "varchar(50)",  remarks: "附件uuid，关联附件表")   
        column(name: "start_date", type: "datetime",  remarks: "有效期从")  {constraints(nullable:"false")}  
        column(name: "end_date", type: "datetime",  remarks: "有效期至")   
        column(name: "published_by", type: "bigint(20)",  remarks: "发布人")   
        column(name: "published_date", type: "datetime",  remarks: "发布时间")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID")  {constraints(nullable:"false")}  
        column(name: "status_code", type: "varchar(60)",  remarks: "公告状态，值集：HPTL.NOTICE.STATUS")  {constraints(nullable:"false")}  
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

    }
   createIndex(tableName: "hptl_notice", indexName: "hptl_notice_n1") {
            column(name: "notice_type_code")
            column(name: "notice_category_code")
            column(name: "tenant_id")
        }          

  }    
}