<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
        </a-form-item>
          
        <a-form-item label="销售单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'saleNo', validatorRules.saleNo]" placeholder="请输入销售单号"></a-input>
        </a-form-item>
          
        <a-form-item label="追溯码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'traceCode', validatorRules.traceCode]" placeholder="请输入追溯码"></a-input>
        </a-form-item>
          
        <a-form-item label="产品批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'productBatch', validatorRules.productBatch]" placeholder="请输入产品批号"></a-input>
        </a-form-item>
          
        <a-form-item label="药材品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'medicineName', validatorRules.medicineName]" placeholder="请输入药材品名"></a-input>
        </a-form-item>
          
        <a-form-item label="药材批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'medicineBatch', validatorRules.medicineBatch]" placeholder="请输入药材批次号"></a-input>
        </a-form-item>
          
        <a-form-item label="收货地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入收货地址"></a-input>
        </a-form-item>
          
        <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contact', validatorRules.contact]" placeholder="请输入联系人"></a-input>
        </a-form-item>
          
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'telephone', validatorRules.telephone]" placeholder="请输入联系电话"></a-input>
        </a-form-item>
          
        <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入规格"></a-input>
        </a-form-item>
          
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'num', validatorRules.num]" placeholder="请输入数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'price', validatorRules.price]" placeholder="请输入单价" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="总价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'totalPrice', validatorRules.totalPrice]" placeholder="请输入总价" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
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
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "WptpSaleModal",
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
        saleNo:{},
        traceCode:{},
        productBatch:{},
        medicineName:{},
        medicineBatch:{},
        address:{},
        contact:{},
        telephone:{},
        guige:{},
        num:{},
        price:{},
        totalPrice:{},
        remark:{},
        auditStatus:{},
        flowId:{},
        deleted:{},
        },
        url: {
          add: "/sale/wptpSale/add",
          edit: "/sale/wptpSale/edit",
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
          this.form.setFieldsValue(pick(this.model,'hostCode','saleNo','traceCode','productBatch','medicineName','medicineBatch','address','contact','telephone','guige','num','price','totalPrice','remark','auditStatus','flowId','deleted'))
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
        this.form.setFieldsValue(pick(row,'hostCode','saleNo','traceCode','productBatch','medicineName','medicineBatch','address','contact','telephone','guige','num','price','totalPrice','remark','auditStatus','flowId','deleted'))
      }
      
    }
  }
</script>