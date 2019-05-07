#!/usr/bin/env bash

# ========== 选择创建模式 ==========
echo "创建服务模式："
echo "	1 - 创建新服务"
echo "	2 - 依赖服务创建"
echo "	3 - 批量创建HZERO服务"

createType=1

read -p "请选择创建服务的模式(1)：" createType

if [ -z "$createType" ]
then 
	createType=1
fi

# ========== 下载maven骨架 ==========

echo "开始创建服务..."

archetypeArtifactId=''

if [ "$createType" == 2 ] 
then 
	archetypeArtifactId='hzero-service-dependency-ddd-archetype'
elif [ "$createType" == 3 ] 
then 
	archetypeArtifactId="hzero-service-dependency-hzero-archetype"
else
	archetypeArtifactId="hzero-service-template-ddd-archetype"
fi

# HZERO 服务版本
read -p "请输入使用的HZERO服务版本(0.8.0.RELEASE)：" hzeroServiceVersion

if [ -z "$hzeroServiceVersion" ]
then 
	hzeroServiceVersion=0.8.0.RELEASE
fi

if [ $createType -lt 3 ] 
then
	if [ "$createType" == 1 ] 
	then 
		# 服务前缀
		read -p "请输入服务前缀：" servicePrefixName
		# 服务简码
		read -p "请输入服务后缀：" serviceSuffixName
		
	elif [ "$createType" == 2 ] 
	then 
		read -p "请输入依赖服务的maven坐标-groupId：" relyServiceGroupId
		read -p "请输入依赖服务的maven坐标-artifactId：" relyServiceArtifactId
		read -p "请输入依赖服务的maven坐标-version：" relyServiceVersion

		# 服务前缀
		read -p "请输入服务前缀：" servicePrefixName
		# 服务简码
		read -p "请输入服务后缀：" serviceSuffixName
	fi

	# 服务名
	serviceName=$(echo $servicePrefixName-$serviceSuffixName)
	# 服务后缀首字母大写
	serviceSuffixNameCapitalize=$(echo ${serviceSuffixName/-/ } | perl -pe '{s/\b\w/\u$&/g}')
	serviceSuffixNameCapitalize=${serviceSuffixNameCapitalize/ /}
	serviceSuffixNamePackage=${serviceSuffixName/-/.}

	# maven 坐标
	read -p "请输入服务maven坐标-groupId(org.$servicePrefixName)：" groupId
	read -p "请输入服务maven坐标-artifactId($serviceName)：" artifactId
	read -p "请输入服务maven坐标-version(0.1.0-SNAPSHOT)：" version


	if [ -z "$groupId" ]
	then 
		groupId=$(echo "org.$servicePrefixName")
	fi
	if [ -z "$artifactId" ]
	then 
		artifactId=$(echo "$serviceName")
	fi
	if [ -z "$version" ]
	then 
		version=0.1.0-SNAPSHOT
	fi

	# 包名
	package=$(echo $groupId"."$serviceSuffixNamePackage)

	read -p "请输入服务maven坐标-package($package)：" package

	if [ -z "$package" ]
	then 
		package=$(echo $groupId"."$serviceSuffixName)
	fi

	# 服务端口
	read -p "请输入服务端口：" serverPort

	# 管理端口
	managementPort=$[$serverPort + 1]

	# 数据库类型
	echo "数据库类型："
	echo "	1 - MySql"
	echo "	2 - Oracle"
	echo "	3 - SqlServer"
	read -p "请选择数据库类型(1)：" databaseType

	if [ -z "$databaseType" ]
	then 
		databaseType=1
	fi

	read -p "请输入数据库Schema：" schemaName

	read -p "请输入数据库用户名：" datasourceUsername

	read -p "请输入数据库密码：" datasourcePassword

	datasourceUrl=''
	dbType=''
	dbArtifactId=''
	dbGroupId=''

	if [ "$databaseType" == 2 ] 
	then 
		datasourceUrl="jdbc:oracle:thin:@db.hzero.org:1521:XE"
		dbType='oracle'
		dbArtifactId='ojdbc7'
		dbGroupId='com.oracle'
	elif [ "$databaseType" == 3 ] 
	then 
		datasourceUrl="jdbc:sqlserver://db.hzero.org:1433;DatabaseName=$schemaName"
		dbType='sqlserver'
		dbArtifactId='sqljdbc4'
		dbGroupId='com.microsoft.sqlserver'
	else
		datasourceUrl="jdbc:mysql://db.hzero.org:3306/$schemaName?useUnicode=true&characterEncoding=utf-8&useSSL=false"
		dbType='mysql'
		dbArtifactId='mysql-connector-java'
		dbGroupId='mysql'
	fi

	echo "生成 $artifactId 中，请稍等..."

	mvn archetype:generate -B \
	 -DarchetypeGroupId=org.hzero \
	 -DarchetypeArtifactId=$archetypeArtifactId \
	 -DarchetypeVersion=0.3.1.RELEASE \
	 -DarchetypeCatalog=local \
	 -DgroupId=$groupId \
	 -DartifactId=$artifactId \
	 -Dversion=$version \
	 -Dpackage=$package \
	 -DhzeroServiceVersion=$hzeroServiceVersion \
	 -DrelyServiceGroupId=$relyServiceGroupId \
	 -DrelyServiceArtifactId=$relyServiceArtifactId \
	 -DrelyServiceVersion=$relyServiceVersion \
	 -DservicePrefixName=$servicePrefixName \
	 -DserviceSuffixName=$serviceSuffixName \
	 -DserviceSuffixNameCapitalize=$serviceSuffixNameCapitalize \
	 -DserviceSuffixNamePackage=$serviceSuffixNamePackage \
	 -DserverPort=$serverPort \
	 -DmanagementPort=$managementPort \
	 -DdbType=$dbType \
	 -DdbArtifactId=$dbArtifactId \
	 -DdbGroupId=$dbGroupId \
	 -DdatasourceUrl=$datasourceUrl \
	 -DdatasourceUsername=$datasourceUsername \
	 -DdatasourcePassword=$datasourcePassword



elif [ "$createType" == 3 ] 
then 
	# OP/SAAS
	echo "项目类型："
	echo "	1 - SAAS 版本"
	echo "	2 - OP 版本"
	read -p "请选择项目类型(1)：" selectHzeroServiceType
	if [ -z "$selectHzeroServiceType" ]
	then 
		selectHzeroServiceType=1
	fi



	# 服务前缀
	read -p "请输入服务前缀：" servicePrefixName
	read -p "请输入服务版本号(0.1.0-SNAPSHOT)：" version
	
	if [ -z "$version" ]
	then 
		version=0.1.0-SNAPSHOT
	fi

	# 数据库类型
	echo "数据库类型："
	echo "	1 - MySql"
	echo "	2 - Oracle"
	echo "	3 - SqlServer"
	read -p "请选择数据库类型(1)：" databaseType

	if [ -z "$databaseType" ]
	then 
		databaseType=1
	fi

	read -p "请输入数据库用户名：" datasourceUsername

	read -p "请输入数据库密码：" datasourcePassword

	datasourceUrl=''
	dbType=''
	dbArtifactId=''
	dbGroupId=''

	if [ "$databaseType" == 2 ] 
	then 
		datasourceUrl="jdbc:oracle:thin:@db.hzero.org:1521:XE"
		dbType='oracle'
		dbArtifactId='ojdbc7'
		dbGroupId='com.oracle'
	elif [ "$databaseType" == 3 ] 
	then 
		datasourceUrl="jdbc:sqlserver://db.hzero.org:1433;DatabaseName=$schemaName"
		dbType='sqlserver'
		dbArtifactId='sqljdbc4'
		dbGroupId='com.microsoft.sqlserver'
	else
		datasourceUrl="jdbc:mysql://db.hzero.org:3306/$schemaName?useUnicode=true&characterEncoding=utf-8&useSSL=false"
		dbType='mysql'
		dbArtifactId='mysql-connector-java'
		dbGroupId='mysql'
	fi

	# 服务列表
	hzeroServiceNameArr=("hzero-register" "hzero-config" "hzero-gateway" "hzero-gateway-helper" "hzero-oauth" "hzero-asgard" "hzero-iam" "hzero-swagger" "hzero-platform" "hzero-file" "hzero-message" "hzero-scheduler" "hzero-import" "hzero-interface" "hzero-workflow" "hzero-workflow-editor" "hzero-portal" "hzero-report")
	hzeroServiceDescArr=("注册中心" "配置服务" "网关服务" "鉴权服务" "认证服务" "事务服务" "IAM用户身份服务" "Swagger测试服务" "平台服务" "文件服务" "消息服务" "调度服务" "通用导入服务" "接口服务" "工作流服务" "工作流设计器" "门户服务" "报表服务")
	hzeroServiceSchemaArr=("hzero_platform" "hzero_governance" "hzero_platform" "hzero_platform" "hzero_platform" "hzero_asgard" "hzero_platform" "hzero_governance" "hzero_platform" "hzero_file" "hzero_message" "hzero_scheduler" "hzero_import" "hzero_interface" "hzero_workflow" "hzero_workflow" "hzero_portal" "hzero_report")
	hzeroServiceSaasArr=(0 1 0 0 0 0 1 0 1 1 1 1 1 1 0 0 1 1)
	heroServiceNumberArr=()

	echo "HZERO 服务列表："
	for(( i=0;i<${#hzeroServiceNameArr[@]};i++)) do
		echo $[i+1] - ${hzeroServiceNameArr[i]}" ("${hzeroServiceDescArr[i]}")";
	done;

	read -p "请选择需要创建的HZERO服务(服务编号之间用逗号隔开，默认创建列表中所有服务)：" selectHeroServiceNumberArr

	if [ -z "$selectHeroServiceNumberArr" ]
	then 
		for(( i=0;i<${#hzeroServiceNameArr[@]};i++)) do
			heroServiceNumberArr[i]=$[i+1]
		done;
	else
		heroServiceNumberArr=(${selectHeroServiceNumberArr//,/ })
	fi


	for(( i=0;i<${#heroServiceNumberArr[@]};i++)) do
		hzeroServiceName=${hzeroServiceNameArr[$[${heroServiceNumberArr[i]}-1]]}
		schemaName=${hzeroServiceSchemaArr[$[${heroServiceNumberArr[i]}-1]]}
		hzeroServiceSaas=${hzeroServiceSaasArr[$[${heroServiceNumberArr[i]}-1]]}
		
		# 数据库地址
		datasourceUrl=''

		if [ "$databaseType" == 2 ] 
		then 
			datasourceUrl="jdbc:oracle:thin:@db.hzero.org:1521:XE"
		elif [ "$databaseType" == 3 ] 
		then 
			datasourceUrl="jdbc:sqlserver://db.hzero.org:1433;DatabaseName=$schemaName"
		else
			datasourceUrl="jdbc:mysql://db.hzero.org:3306/$schemaName?useUnicode=true&characterEncoding=utf-8&useSSL=false"
		fi

		# 判断SaaS还是OP
		hzeroServiceType=""
		if [ "$selectHzeroServiceType" == 1 -a "$hzeroServiceSaas" == 1 ] 
		then 
			hzeroServiceType="-saas"
		else
			hzeroServiceType=""
		fi

		
		# 取服务后缀
		serviceSuffixName=${hzeroServiceName#hzero-}
		# 服务后缀首字母大写
		serviceSuffixNameCapitalize=$(echo ${serviceSuffixName/-/ } | perl -pe '{s/\b\w/\u$&/g}')
		serviceSuffixNameCapitalize=${serviceSuffixNameCapitalize/ /}
		serviceSuffixNamePackage=${serviceSuffixName/-/.}
		# 拼接 -saas 后缀
		hzeroServiceArtifactSuffix=$(echo $serviceSuffixName$hzeroServiceType)

		groupId=$(echo "org.$servicePrefixName")
		artifactId=$(echo "$servicePrefixName-$serviceSuffixName")
		serviceName=$(echo "$servicePrefixName-$serviceSuffixName")
		version=0.1.0-SNAPSHOT

		package=$(echo $groupId"."$serviceSuffixNamePackage)

		# 启动类方法
		applicationPackage=''
		applicationMain=''
		applicationAnnotation=''
		if [ "$hzeroServiceName" == "hzero-iam" -o "$hzeroServiceName" == "hzero-config" -o "$hzeroServiceName" == "hzero-swagger" -o "$hzeroServiceName" == "hzero-asgard" ]
		then 
			applicationPackage=$(echo -e "import org.hzero.autoconfigure.$serviceSuffixNamePackage.EnableHZero$serviceSuffixNameCapitalize;\nimport io.choerodon.eureka.event.EurekaEventHandler;\nimport org.springframework.cloud.netflix.eureka.EnableEurekaClient;")
			applicationMain=$(echo -e "EurekaEventHandler.getInstance().init();\n        SpringApplication.run(${serviceSuffixNameCapitalize}Application.class, args);")
		else
			applicationPackage=$(echo -e "import org.hzero.autoconfigure.$serviceSuffixNamePackage.EnableHZero$serviceSuffixNameCapitalize;\nimport org.springframework.cloud.netflix.eureka.EnableEurekaClient;")
			applicationMain=$(echo "SpringApplication.run(${serviceSuffixNameCapitalize}Application.class, args);")
		fi	
		if [ "$hzeroServiceName" == "hzero-config" ]
		then 
			applicationPackage=$(echo -e "$applicationPackage\nimport org.springframework.cloud.config.server.config.ConfigServerAutoConfiguration;")
			applicationAnnotation=$(echo -e "@SpringBootApplication(exclude = {ConfigServerAutoConfiguration.class})\n@EnableEurekaClient")
		elif [ "$hzeroServiceName" == "hzero-register" ]
		then
			applicationPackage=$(echo -e "import org.hzero.autoconfigure.$serviceSuffixNamePackage.EnableHZero$serviceSuffixNameCapitalize;\nimport org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;\nimport org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration;\nimport org.springframework.context.annotation.Import;")
			applicationAnnotation=$(echo -e "@EnableEurekaServer\n@SpringBootApplication\n@Import(EurekaServerAutoConfiguration.class)")
		else
			applicationAnnotation=$(echo -e "@EnableEurekaClient\n@SpringBootApplication")
		fi	

		echo "生成 $artifactId 中，请稍等..."

		mvn archetype:generate -B \
		 -DarchetypeGroupId=org.hzero \
		 -DarchetypeArtifactId=$archetypeArtifactId \
		 -DarchetypeVersion=0.3.1.RELEASE \
		 -DarchetypeCatalog=local \
		 -DgroupId=$groupId \
		 -DartifactId=$artifactId \
		 -Dversion=$version \
		 -Dpackage=$package \
		 -DhzeroServiceVersion=$hzeroServiceVersion \
		 -DhzeroServiceArtifactSuffix=$hzeroServiceArtifactSuffix \
		 -DserviceSuffixName=$serviceSuffixName \
		 -DserviceSuffixNameCapitalize=$serviceSuffixNameCapitalize \
		 -DservicePrefixName=$servicePrefixName \
		 -DdbType=$dbType \
		 -DdbArtifactId=$dbArtifactId \
		 -DdbGroupId=$dbGroupId \
		 -DdatasourceUrl=$datasourceUrl \
		 -DdatasourceUsername=$datasourceUsername \
		 -DdatasourcePassword=$datasourcePassword \
		 -DapplicationMainPackage="$applicationPackage" \
		 -DapplicationMainBody="$applicationMain" \
		 -DapplicationAnnotation="$applicationAnnotation"

		echo "下载 $hzeroServiceName 配置文件，请稍等..."
		curl https://code.choerodon.com.cn/16007/hzero/raw/master/$hzeroServiceName/application.yml -o ./application.yml
		curl https://code.choerodon.com.cn/16007/hzero/raw/master/$hzeroServiceName/bootstrap.yml -o ./bootstrap.yml
		curl https://code.choerodon.com.cn/16007/hzero/raw/master/$hzeroServiceName/values.yaml -o ./values.yaml

		sed -i "s#datasourceUrl#$datasourceUrl#g" ./application.yml
		sed -i "s#datasourceUrl#\&#g" ./application.yml
		sed -i "s/datasourceUsername/$datasourceUsername/g" ./application.yml
		sed -i "s/datasourcePassword/$datasourcePassword/g" ./application.yml

		sed -i "s#datasourceUrl#$datasourceUrl#g" ./values.yaml
		sed -i "s#datasourceUrl#\&#g" ./values.yaml
		sed -i "s/datasourceUsername/$datasourceUsername/g" ./values.yaml
		sed -i "s/datasourcePassword/$datasourcePassword/g" ./values.yaml
		sed -i "s/serviceName/$serviceName/g" ./values.yaml
 		
		mv ./application.yml $artifactId/src/main/resources/
		mv ./bootstrap.yml $artifactId/src/main/resources/
		mv ./values.yaml $artifactId/charts/$artifactId/

	done;


fi


if [ "$createType" == 1 ] 
then 
	echo "创建 $serviceName 服务完成."
elif [ "$createType" == 2 ] 
then 
	echo "依赖 $relyServiceArtifactId 创建 $serviceName 服务完成. 请从依赖服务 jar 包拷贝启动类及配置文件，保持一致."
else
	echo "批量创建HZERO服务完成."
fi


