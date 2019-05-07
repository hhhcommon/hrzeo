package script.db

databaseChangeLog(logicalFilePath: 'script/db/hptl_templates_config_item.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2018-10-25-hptl_templates_config_item") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hptl_templates_config_item_s', startValue:"1")
        }
        createTable(tableName: "hptl_templates_config_item", remarks: "门户模板配置明细") {
        column(name: "config_item_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
        column(name: "config_id", type: "bigint(20)",  remarks: "配置ID HPTL_TEMPLATES_CONFIG.CONFIG_ID")  {constraints(nullable:"false")}  
        column(name: "config_code", type: "varchar(50)",  remarks: "配置编码")  {constraints(nullable:"false")}  
        column(name: "image_url", type: "varchar(255)",  remarks: "配置图片")   
        column(name: "content", type: "varchar(255)",  remarks: "标题")   
        column(name: "description", type: "varchar(500)",  remarks: "副标题")   
        column(name: "link_url", type: "varchar(240)",  remarks: "链接")   
        column(name: "order_seq", type: "int(11)",   defaultValue:"1",   remarks: "排序号")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

    }
   createIndex(tableName: "hptl_templates_config_item", indexName: "hptl_templates_config_item_n1") {
            column(name: "config_id")
        }          

  }    
}