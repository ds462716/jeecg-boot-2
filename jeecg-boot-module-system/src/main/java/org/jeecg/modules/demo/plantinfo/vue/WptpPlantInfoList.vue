<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('田间作业表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <wptpPlantInfo-modal ref="modalForm" @ok="modalFormOk"></wptpPlantInfo-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WptpPlantInfoModal from './modules/WptpPlantInfoModal'
  export default {
    name: "WptpPlantInfoList",
    mixins:[JeecgListMixin],
    components: {
      WptpPlantInfoModal
    },
    data () {
      return {
        description: '田间作业表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'主机代码',
            align:"center",
            dataIndex: 'hostCode'
          },
          {
            title:'地块药材编号',
            align:"center",
            dataIndex: 'blockMedicinalId'
          },
          {
            title:'作业编号',
            align:"center",
            dataIndex: 'plantId'
          },
          {
            title:'采收批次号',
            align:"center",
            dataIndex: 'batchCode'
          },
          {
            title:'作业类别',
            align:"center",
            dataIndex: 'plantSatus'
          },
          {
            title:'繁殖方法',
            align:"center",
            dataIndex: 'plantMethod'
          },
          {
            title:'繁殖时间',
            align:"center",
            dataIndex: 'fzTime'
          },
          {
            title:'操作时间',
            align:"center",
            dataIndex: 'operateTime'
          },
          {
            title:'投入品',
            align:"center",
            dataIndex: 'inputInto'
          },
          {
            title:'量',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'采收部位/繁殖地点',
            align:"center",
            dataIndex: 'csPart'
          },
          {
            title:'来源渠道',
            align:"center",
            dataIndex: 'source'
          },
          {
            title:'植物来源',
            align:"center",
            dataIndex: 'plantSource'
          },
          {
            title:'责任人',
            align:"center",
            dataIndex: 'responsible'
          },
          {
            title:'审核标志',
            align:"center",
            dataIndex: 'auditStatus'
          },
          {
            title:'工作流',
            align:"center",
            dataIndex: 'flowId'
          },
          {
            title:'删除标志',
            align:"center",
            dataIndex: 'deleted'
          },
          {
            title:'作业方式',
            align:"center",
            dataIndex: 'plantType'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/plantinfo/wptpPlantInfo/list",
          delete: "/plantinfo/wptpPlantInfo/delete",
          deleteBatch: "/plantinfo/wptpPlantInfo/deleteBatch",
          exportXlsUrl: "/plantinfo/wptpPlantInfo/exportXls",
          importExcelUrl: "plantinfo/wptpPlantInfo/importExcel",
        },
        dictOptions:{
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>