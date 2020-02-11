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
            <a-form-item label="地块药材编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'blockMedicinalId', validatorRules.blockMedicinalId]" placeholder="请输入地块药材编号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="作业编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantId', validatorRules.plantId]" placeholder="请输入作业编号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采收批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'batchCode', validatorRules.batchCode]" placeholder="请输入采收批次号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="作业类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantSatus', validatorRules.plantSatus]" placeholder="请输入作业类别"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="繁殖方法" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantMethod', validatorRules.plantMethod]" placeholder="请输入繁殖方法"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="繁殖时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'fzTime', validatorRules.fzTime]" placeholder="请输入繁殖时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="操作时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'operateTime', validatorRules.operateTime]" placeholder="请输入操作时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="投入品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'inputInto', validatorRules.inputInto]" placeholder="请输入投入品"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'number', validatorRules.number]" placeholder="请输入量"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采收部位/繁殖地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'csPart', validatorRules.csPart]" placeholder="请输入采收部位/繁殖地点"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="来源渠道" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'source', validatorRules.source]" placeholder="请输入来源渠道"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="植物来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantSource', validatorRules.plantSource]" placeholder="请输入植物来源"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="责任人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'responsible', validatorRules.responsible]" placeholder="请输入责任人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核标志"></a-input>
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
            <a-form-item label="作业方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantType', validatorRules.plantType]" placeholder="请输入作业方式"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="田间作业附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpPlantFileTable.loading"
            :columns="wptpPlantFileTable.columns"
            :dataSource="wptpPlantFileTable.dataSource"
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
    name: 'WptpPlantInfoModal',
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
          blockMedicinalId:{},
          plantId:{},
          batchCode:{},
          plantSatus:{},
          plantMethod:{},
          fzTime:{},
          operateTime:{},
          inputInto:{},
          number:{},
          csPart:{},
          source:{},
          plantSource:{},
          responsible:{},
          auditStatus:{},
          flowId:{},
          deleted:{},
          plantType:{},
        },
        refKeys: ['wptpPlantFile', ],
        tableKeys:['wptpPlantFile', ],
        activeKey: 'wptpPlantFile',
        // 田间作业附表
        wptpPlantFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '作业编号',
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
          add: "/plantinfo/wptpPlantInfo/add",
          edit: "/plantinfo/wptpPlantInfo/edit",
          wptpPlantFile: {
            list: '/plantinfo/wptpPlantInfo/queryWptpPlantFileByMainId'
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
        let fieldval = pick(this.model,'hostCode','blockMedicinalId','plantId','batchCode','plantSatus','plantMethod','fzTime','operateTime','inputInto','number','csPart','source','plantSource','responsible','auditStatus','flowId','deleted','plantType')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpPlantFile.list, params, this.wptpPlantFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpPlantFileList: allValues.tablesValue[0].values,
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