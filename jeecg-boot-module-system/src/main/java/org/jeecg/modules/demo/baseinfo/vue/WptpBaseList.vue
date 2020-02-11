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
      <a-button type="primary" icon="download" @click="handleExportXls('基地信息管理表')">导出</a-button>
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

    <wptpBase-modal ref="modalForm" @ok="modalFormOk"></wptpBase-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WptpBaseModal from './modules/WptpBaseModal'
  export default {
    name: "WptpBaseList",
    mixins:[JeecgListMixin],
    components: {
      WptpBaseModal
    },
    data () {
      return {
        description: '基地信息管理表管理页面',
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
            title:'基地编码',
            align:"center",
            dataIndex: 'baseCode'
          },
          {
            title:'基地名称',
            align:"center",
            dataIndex: 'baseName'
          },
          {
            title:'省份id',
            align:"center",
            dataIndex: 'province'
          },
          {
            title:'市id',
            align:"center",
            dataIndex: 'city'
          },
          {
            title:'区（县）id',
            align:"center",
            dataIndex: 'area'
          },
          {
            title:'基地位置',
            align:"center",
            dataIndex: 'baseAddress'
          },
          {
            title:'经度',
            align:"center",
            dataIndex: 'gpsLongitude'
          },
          {
            title:'纬度',
            align:"center",
            dataIndex: 'gpsLatitude'
          },
          {
            title:'基地面积',
            align:"center",
            dataIndex: 'baseArea'
          },
          {
            title:'地形',
            align:"center",
            dataIndex: 'land'
          },
          {
            title:'海拔',
            align:"center",
            dataIndex: 'heigh'
          },
          {
            title:'土壤类型',
            align:"center",
            dataIndex: 'soilType'
          },
          {
            title:'酸碱度',
            align:"center",
            dataIndex: 'ph'
          },
          {
            title:'气候类型',
            align:"center",
            dataIndex: 'weatherType'
          },
          {
            title:'年降雨量',
            align:"center",
            dataIndex: 'rearRainfall'
          },
          {
            title:'年平均温度',
            align:"center",
            dataIndex: 'temperature'
          },
          {
            title:'水源',
            align:"center",
            dataIndex: 'waterSrc'
          },
          {
            title:'企业编号',
            align:"center",
            dataIndex: 'entId'
          },
          {
            title:'所属基地公司',
            align:"center",
            dataIndex: 'baseEnt'
          },
          {
            title:'工作流id',
            align:"center",
            dataIndex: 'flowId'
          },
          {
            title:'审核状态',
            align:"center",
            dataIndex: 'auditStatus'
          },
          {
            title:'删除状态',
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
          list: "/baseinfo/wptpBase/list",
          delete: "/baseinfo/wptpBase/delete",
          deleteBatch: "/baseinfo/wptpBase/deleteBatch",
          exportXlsUrl: "/baseinfo/wptpBase/exportXls",
          importExcelUrl: "baseinfo/wptpBase/importExcel",
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