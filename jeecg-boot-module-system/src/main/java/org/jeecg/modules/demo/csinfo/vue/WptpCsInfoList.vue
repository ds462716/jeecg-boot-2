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
      <a-button type="primary" icon="download" @click="handleExportXls('采收批次信息')">导出</a-button>
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

    <wptpCsInfo-modal ref="modalForm" @ok="modalFormOk"></wptpCsInfo-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WptpCsInfoModal from './modules/WptpCsInfoModal'
  export default {
    name: "WptpCsInfoList",
    mixins:[JeecgListMixin],
    components: {
      WptpCsInfoModal
    },
    data () {
      return {
        description: '采收批次信息管理页面',
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
            title:'采收批次号',
            align:"center",
            dataIndex: 'csNo'
          },
          {
            title:'规格',
            align:"center",
            dataIndex: 'guige'
          },
          {
            title:'重量',
            align:"center",
            dataIndex: 'weight'
          },
          {
            title:'单位',
            align:"center",
            dataIndex: 'unit'
          },
          {
            title:'生产档案编号',
            align:"center",
            dataIndex: 'blockMedicinalId'
          },
          {
            title:'药材品种',
            align:"center",
            dataIndex: 'medicinalCode'
          },
          {
            title:'所属企业',
            align:"center",
            dataIndex: 'entId'
          },
          {
            title:'所属基地',
            align:"center",
            dataIndex: 'baseCode'
          },
          {
            title:'地块编号',
            align:"center",
            dataIndex: 'blockCode'
          },
          {
            title:'标志',
            align:"center",
            dataIndex: 'flag'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/csinfo/wptpCsInfo/list",
          delete: "/csinfo/wptpCsInfo/delete",
          deleteBatch: "/csinfo/wptpCsInfo/deleteBatch",
          exportXlsUrl: "/csinfo/wptpCsInfo/exportXls",
          importExcelUrl: "csinfo/wptpCsInfo/importExcel",
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