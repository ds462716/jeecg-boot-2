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
            <a-form-item label="基地编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'baseCode', validatorRules.baseCode]" placeholder="请输入基地编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="基地名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'baseName', validatorRules.baseName]" placeholder="请输入基地名称"></a-input>
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
            <a-form-item label="区（县）id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区（县）id"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="基地位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'baseAddress', validatorRules.baseAddress]" placeholder="请输入基地位置"></a-input>
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
            <a-form-item label="基地面积" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'baseArea', validatorRules.baseArea]" placeholder="请输入基地面积"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="地形" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'land', validatorRules.land]" placeholder="请输入地形"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="海拔" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'heigh', validatorRules.heigh]" placeholder="请输入海拔"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="土壤类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'soilType', validatorRules.soilType]" placeholder="请输入土壤类型"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="酸碱度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ph', validatorRules.ph]" placeholder="请输入酸碱度"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="气候类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'weatherType', validatorRules.weatherType]" placeholder="请输入气候类型"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="年降雨量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'rearRainfall', validatorRules.rearRainfall]" placeholder="请输入年降雨量"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="年平均温度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'temperature', validatorRules.temperature]" placeholder="请输入年平均温度"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="水源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'waterSrc', validatorRules.waterSrc]" placeholder="请输入水源"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="企业编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入企业编号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="所属基地公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'baseEnt', validatorRules.baseEnt]" placeholder="请输入所属基地公司"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="工作流id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流id"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核状态"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除状态"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="基地信息附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpBaseFileTable.loading"
            :columns="wptpBaseFileTable.columns"
            :dataSource="wptpBaseFileTable.dataSource"
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
    name: 'WptpBaseModal',
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
          baseCode:{},
          baseName:{},
          province:{},
          city:{},
          area:{},
          baseAddress:{},
          gpsLongitude:{},
          gpsLatitude:{},
          baseArea:{},
          land:{},
          heigh:{},
          soilType:{},
          ph:{},
          weatherType:{},
          rearRainfall:{},
          temperature:{},
          waterSrc:{},
          entId:{},
          baseEnt:{},
          flowId:{},
          auditStatus:{},
          deleted:{},
        },
        refKeys: ['wptpBaseFile', ],
        tableKeys:['wptpBaseFile', ],
        activeKey: 'wptpBaseFile',
        // 基地信息附表
        wptpBaseFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '创建人',
              key: 'createBy',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '创建日期',
              key: 'createTime',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '更新人',
              key: 'updateBy',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '更新日期',
              key: 'updateTime',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '基地编码',
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
              title: '类型0：基地图片；1：基地报告',
              key: 'type',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '0:图片；1：视频',
              key: 'fileType',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '删除标志',
              key: 'deleted',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/baseinfo/wptpBase/add",
          edit: "/baseinfo/wptpBase/edit",
          wptpBaseFile: {
            list: '/baseinfo/wptpBase/queryWptpBaseFileByMainId'
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
        let fieldval = pick(this.model,'hostCode','baseCode','baseName','province','city','area','baseAddress','gpsLongitude','gpsLatitude','baseArea','land','heigh','soilType','ph','weatherType','rearRainfall','temperature','waterSrc','entId','baseEnt','flowId','auditStatus','deleted')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpBaseFile.list, params, this.wptpBaseFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpBaseFileList: allValues.tablesValue[0].values,
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