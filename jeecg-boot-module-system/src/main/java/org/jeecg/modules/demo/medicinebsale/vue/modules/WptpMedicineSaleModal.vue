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
          
        <a-form-item label="饮片销售流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'saleNumber', validatorRules.saleNumber]" placeholder="请输入饮片销售流水号"></a-input>
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
          
        <a-form-item label="药材规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入药材规格"></a-input>
        </a-form-item>
          
        <a-form-item label="种类编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'categoryCode', validatorRules.categoryCode]" placeholder="请输入种类编码"></a-input>
        </a-form-item>
          
        <a-form-item label="种类名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'categoryName', validatorRules.categoryName]" placeholder="请输入种类名称"></a-input>
        </a-form-item>
          
        <a-form-item label="主数据码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mainCode', validatorRules.mainCode]" placeholder="请输入主数据码"></a-input>
        </a-form-item>
          
        <a-form-item label="药材入库流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'instockNumber', validatorRules.instockNumber]" placeholder="请输入药材入库流水号"></a-input>
        </a-form-item>
          
        <a-form-item label="出库时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'outTime', validatorRules.outTime]" placeholder="请输入出库时间"></a-input>
        </a-form-item>
          
        <a-form-item label="出库数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'num', validatorRules.num]" placeholder="请输入出库数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'customer', validatorRules.customer]" placeholder="请输入客户"></a-input>
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
          
        <a-form-item label="运输责任人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'responsible', validatorRules.responsible]" placeholder="请输入运输责任人"></a-input>
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
          
        <a-form-item label="销售明细序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'saleNum', validatorRules.saleNum]" placeholder="请输入销售明细序列号"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "WptpMedicineSaleModal",
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
        saleNumber:{},
        saleNo:{},
        traceCode:{},
        productBatch:{},
        medicineName:{},
        guige:{},
        categoryCode:{},
        categoryName:{},
        mainCode:{},
        instockNumber:{},
        outTime:{},
        num:{},
        customer:{},
        address:{},
        contact:{},
        telephone:{},
        responsible:{},
        auditStatus:{},
        flowId:{},
        deleted:{},
        saleNum:{},
        },
        url: {
          add: "/medicinebsale/wptpMedicineSale/add",
          edit: "/medicinebsale/wptpMedicineSale/edit",
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
          this.form.setFieldsValue(pick(this.model,'hostCode','saleNumber','saleNo','traceCode','productBatch','medicineName','guige','categoryCode','categoryName','mainCode','instockNumber','outTime','num','customer','address','contact','telephone','responsible','auditStatus','flowId','deleted','saleNum'))
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
        this.form.setFieldsValue(pick(row,'hostCode','saleNumber','saleNo','traceCode','productBatch','medicineName','guige','categoryCode','categoryName','mainCode','instockNumber','outTime','num','customer','address','contact','telephone','responsible','auditStatus','flowId','deleted','saleNum'))
      }
      
    }
  }
</script>