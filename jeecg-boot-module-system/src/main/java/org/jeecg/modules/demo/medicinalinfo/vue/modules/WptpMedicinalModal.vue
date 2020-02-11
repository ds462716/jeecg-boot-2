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
            <a-form-item label="药材种类编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'medicinalCode', validatorRules.medicinalCode]" placeholder="请输入药材种类编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="用药部位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'yybw', validatorRules.yybw]" placeholder="请输入用药部位"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="生长环境" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'szhj', validatorRules.szhj]" placeholder="请输入生长环境"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="功效与主治" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'func', validatorRules.func]" placeholder="请输入功效与主治"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采收时期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'cssq', validatorRules.cssq]" placeholder="请输入采收时期"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核标志"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除状态"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="工作流ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流ID"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="药材信息附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpMedicineFileTable.loading"
            :columns="wptpMedicineFileTable.columns"
            :dataSource="wptpMedicineFileTable.dataSource"
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
    name: 'WptpMedicinalModal',
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
          medicinalCode:{},
          name:{},
          yybw:{},
          szhj:{},
          func:{},
          cssq:{},
          auditStatus:{},
          deleted:{},
          flowId:{},
        },
        refKeys: ['wptpMedicineFile', ],
        tableKeys:['wptpMedicineFile', ],
        activeKey: 'wptpMedicineFile',
        // 药材信息附表
        wptpMedicineFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '药材编码',
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
          add: "/medicinalinfo/wptpMedicinal/add",
          edit: "/medicinalinfo/wptpMedicinal/edit",
          wptpMedicineFile: {
            list: '/medicinalinfo/wptpMedicinal/queryWptpMedicineFileByMainId'
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
        let fieldval = pick(this.model,'medicinalCode','name','yybw','szhj','func','cssq','auditStatus','deleted','flowId')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpMedicineFile.list, params, this.wptpMedicineFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpMedicineFileList: allValues.tablesValue[0].values,
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