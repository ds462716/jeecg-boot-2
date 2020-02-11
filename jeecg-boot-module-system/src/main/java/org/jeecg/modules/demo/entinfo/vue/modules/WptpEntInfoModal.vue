<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="企业编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入企业编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="企业类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entType', validatorRules.entType]" placeholder="请输入企业类别"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="组织机构代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'orgCode', validatorRules.orgCode]" placeholder="请输入组织机构代码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entPrincipal', validatorRules.entPrincipal]" placeholder="请输入法人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="授权人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'authorize', validatorRules.authorize]" placeholder="请输入授权人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入联系电话"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'regCapi', validatorRules.regCapi]" placeholder="请输入注册资金"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="工商注册号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'busRegisNum', validatorRules.busRegisNum]" placeholder="请输入工商注册号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="企业地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entAdd', validatorRules.entAdd]" placeholder="请输入企业地址"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="成立时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'establiTime', validatorRules.establiTime]" placeholder="请输入成立时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="企业网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entWebsite', validatorRules.entWebsite]" placeholder="请输入企业网址"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="省份id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省份id"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="市id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'city', validatorRules.city]" placeholder="请输入市id"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="区id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区id"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地址"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="主体介绍" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'mainIntro', validatorRules.mainIntro]" placeholder="请输入主体介绍"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'gpsLongitude', validatorRules.gpsLongitude]" placeholder="请输入经度"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'gpsLatitude', validatorRules.gpsLatitude]" placeholder="请输入纬度"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核状态"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="工作流id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流id"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entName', validatorRules.entName]" placeholder="请输入企业名称"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="企业信息附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpEntFileTable.loading"
            :columns="wptpEntFileTable.columns"
            :dataSource="wptpEntFileTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'

  export default {
    name: 'WptpEntInfoModal',
    mixins: [JEditableTableMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          hostCode:{},
          entId:{},
          entType:{},
          orgCode:{},
          entPrincipal:{},
          authorize:{},
          phone:{},
          regCapi:{},
          busRegisNum:{},
          entAdd:{},
          establiTime:{},
          entWebsite:{},
          province:{},
          city:{},
          area:{},
          address:{},
          mainIntro:{},
          gpsLongitude:{},
          gpsLatitude:{},
          deleted:{},
          auditStatus:{},
          flowId:{},
          entName:{},
        },
        refKeys: ['wptpEntFile', ],
        tableKeys:['wptpEntFile', ],
        activeKey: 'wptpEntFile',
        // 企业信息附表
        wptpEntFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '企业编号',
              key: 'mainId',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '路径',
              key: 'path',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '文件类型 0：图片；1：视频；2：pdf',
              key: 'fileType',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '1：证件照片',
              key: 'type',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '0:未删除；1：删除',
              key: 'deleted',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/entinfo/wptpEntInfo/add",
          edit: "/entinfo/wptpEntInfo/edit",
          wptpEntFile: {
            list: '/entinfo/wptpEntInfo/queryWptpEntFileByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'hostCode','entId','entType','orgCode','entPrincipal','authorize','phone','regCapi','busRegisNum','entAdd','establiTime','entWebsite','province','city','area','address','mainIntro','gpsLongitude','gpsLatitude','deleted','auditStatus','flowId','entName')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpEntFile.list, params, this.wptpEntFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpEntFileList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      }
      
      
    }
  }
</script>

<style scoped>
</style>