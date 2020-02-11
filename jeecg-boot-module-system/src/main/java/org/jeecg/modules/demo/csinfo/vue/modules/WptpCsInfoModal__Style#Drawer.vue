<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
        </a-form-item>
        <a-form-item label="采收批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'csNo', validatorRules.csNo]" placeholder="请输入采收批次号"></a-input>
        </a-form-item>
        <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入规格"></a-input>
        </a-form-item>
        <a-form-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'weight', validatorRules.weight]" placeholder="请输入重量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'unit', validatorRules.unit]" placeholder="请输入单位"></a-input>
        </a-form-item>
        <a-form-item label="生产档案编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'blockMedicinalId', validatorRules.blockMedicinalId]" placeholder="请输入生产档案编号"></a-input>
        </a-form-item>
        <a-form-item label="药材品种" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'medicinalCode', validatorRules.medicinalCode]" placeholder="请输入药材品种"></a-input>
        </a-form-item>
        <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入所属企业"></a-input>
        </a-form-item>
        <a-form-item label="所属基地" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'baseCode', validatorRules.baseCode]" placeholder="请输入所属基地"></a-input>
        </a-form-item>
        <a-form-item label="地块编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'blockCode', validatorRules.blockCode]" placeholder="请输入地块编号"></a-input>
        </a-form-item>
        <a-form-item label="标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'flag', validatorRules.flag]" placeholder="请输入标志"></a-input>
        </a-form-item>
        <a-form-item label="审核标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核标志"></a-input>
        </a-form-item>
        <a-form-item label="工作流" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流"></a-input>
        </a-form-item>
        <a-form-item label="删除标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "WptpCsInfoModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        hostCode:{},
        csNo:{},
        guige:{},
        weight:{},
        unit:{},
        blockMedicinalId:{},
        medicinalCode:{},
        entId:{},
        baseCode:{},
        blockCode:{},
        flag:{},
        auditStatus:{},
        flowId:{},
        deleted:{},
        },
        url: {
          add: "/csinfo/wptpCsInfo/add",
          edit: "/csinfo/wptpCsInfo/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'hostCode','csNo','guige','weight','unit','blockMedicinalId','medicinalCode','entId','baseCode','blockCode','flag','auditStatus','flowId','deleted'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'hostCode','csNo','guige','weight','unit','blockMedicinalId','medicinalCode','entId','baseCode','blockCode','flag','auditStatus','flowId','deleted'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>