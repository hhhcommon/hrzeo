package script.db

databaseChangeLog(logicalFilePath: 'script/db/hfle_storage_config.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hfle_storage_config") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hfle_storage_config_s', startValue:"1")
        }
        createTable(tableName: "hfle_storage_config", remarks: "") {
            column(name: "storage_config_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "storage_type", type: "tinyint(1)",  remarks: "类型 1:阿里 2:华为 3:Minio 4:腾讯 5:七牛")  {constraints(nullable:"false")}  
            column(name: "domain", type: "varchar(" + 120 * weight + ")",  remarks: "绑定的域名")   
            column(name: "end_point", type: "varchar(" + 120 * weight + ")",  remarks: "EndPoint")   
            column(name: "access_key_id", type: "varchar(" + 120 * weight + ")",  remarks: "AccessKeyId")   
            column(name: "access_key_secret", type: "varchar(" + 120 * weight + ")",  remarks: "AccessKeySecret")   
            column(name: "app_id", type: "bigint(20)",  remarks: "腾讯云 AppId")   
            column(name: "region", type: "varchar(" + 120 * weight + ")",  remarks: "腾讯云 所在区域")   
            column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "default_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "默认标识")  {constraints(nullable:"false")}  
            column(name: "access_control", type: "varchar(" + 120 * weight + ")",  remarks: "bucket权限控制")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"storage_type,tenant_id",tableName:"hfle_storage_config",constraintName: "hfle_storage_config_u1")
    }

    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-17-hfle_storage_config") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: "hfle_storage_config"){
            column(name: "bucket_prefix", type: "varchar(" + 60 * weight + ")", remarks: "Bucket前缀")
        }
    }
}