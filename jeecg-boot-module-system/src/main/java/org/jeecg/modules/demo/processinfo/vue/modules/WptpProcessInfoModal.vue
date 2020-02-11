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
            <a-form-item label="加工单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'processNo', validatorRules.processNo]" placeholder="请输入加工单号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="投料批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'csBatch', validatorRules.csBatch]" placeholder="请输入投料批次"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="投料数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'materialNum', validatorRules.materialNum]" placeholder="请输入投料数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="加工工艺" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'processGy', validatorRules.processGy]" placeholder="请输入加工工艺"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="加工方法" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'processMethod', validatorRules.processMethod]" placeholder="请输入加工方法"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="加工开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'startTime', validatorRules.startTime]" placeholder="请输入加工开始时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="加工结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'endTime', validatorRules.endTime]" placeholder="请输入加工结束时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'responsible', validatorRules.responsible]" placeholder="请输入负责人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="加工数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'num', validatorRules.num]" placeholder="请输入加工数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="加工设备" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'device', validatorRules.device]" placeholder="请输入加工设备"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="工作流" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核标志"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="产地初加工附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpProcessFileTable.loading"
            :columns="wptpProcessFileTable.columns"
            :dataSource="wptpProcessFileTable.dataSource"
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
    name: 'WptpProcessInfoModal',
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
          processNo:{},
          csBatch:{},
          materialNum:{},
          processGy:{},
          processMethod:{},
          startTime:{},
          endTime:{},
          responsible:{},
          num:{},
          device:{},
          flowId:{},
          deleted:{},
          auditStatus:{},
        },
        refKeys: ['wptpProcessFile', ],
        tableKeys:['wptpProcessFile', ],
        activeKey: 'wptpProcessFile',
        // 产地初加工附表
        wptpProcessFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '加工单号',
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
              title: '类型',
              key: 'type',
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
            {
              title: '文件类型',
              key: 'fileType',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/processinfo/wptpProcessInfo/add",
          edit: "/processinfo/wptpProcessInfo/edit",
          wptpProcessFile: {
            list: '/processinfo/wptpProcessInfo/queryWptpProcessFileByMainId'
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
        let fieldval = pick(this.model,'hostCode','processNo','csBatch','materialNum','processGy','processMethod','startTime','endTime','responsible','num','device','flowId','deleted','auditStatus')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpProcessFile.list, params, this.wptpProcessFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpProcessFileList: allValues.tablesValue[0].values,
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