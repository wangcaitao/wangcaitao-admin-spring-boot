drop table if exists dict;
create table dict
(
    id           bigint unsigned                        not null comment 'id' primary key auto_increment,
    parent_code  varchar(32)  default ''                not null comment '父级编码',
    code         varchar(32)                            not null comment '编码',
    name         varchar(32)                            not null comment '名称',
    sort         int unsigned default 1                 not null comment '排序',
    create_gmt   datetime     default current_timestamp not null comment '创建时间',
    modified_gmt datetime     default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '字典表';


drop table if exists property;
create table property
(
    id           bigint unsigned                        not null comment 'id' primary key auto_increment,
    name         varchar(255)                           not null comment '属性名',
    value        varchar(255)                           not null comment '属性值',
    description  varchar(255) default ''                not null comment '说明',
    create_gmt   datetime     default current_timestamp not null comment '创建时间',
    modified_gmt datetime     default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '属性表';


drop table if exists resource;
create table resource
(
    id              bigint unsigned                            not null comment 'id' primary key auto_increment,
    status_code     tinyint unsigned default 1                 not null comment '状态编码. 1: 启用, 2: 禁用',
    name            varchar(32)                                not null comment '名称',
    url             varchar(128)     default ''                not null comment '地址',
    request_method  varchar(6)       default 'GET'             not null comment '请求方式. GET, POST, PUT, DELETE',
    sort            int unsigned     default 1                 not null comment '排序',
    type_code       tinyint unsigned                           not null comment '类型编码. 1: 菜单, 2: 按钮',
    icon            varchar(255)     default ''                not null comment '图标',
    top_status_code tinyint unsigned default 1                 not null comment '是否顶级. 0: 不是, 1: 是',
    create_gmt      datetime         default current_timestamp not null comment '创建时间',
    modified_gmt    datetime         default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '资源表';


drop table if exists resource_relation;
create table resource_relation
(
    id           bigint unsigned                    not null comment 'id' primary key auto_increment,
    parent_id    bigint unsigned                    not null comment '父节点 id',
    child_id     bigint unsigned                    not null comment '子节点 id',
    depth        int unsigned                       not null comment '深度',
    create_gmt   datetime default current_timestamp not null comment '创建时间',
    modified_gmt datetime default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '资源关系表';


drop table if exists position;
create table position
(
    id           bigint unsigned                            not null comment 'id' primary key auto_increment,
    name         varchar(20)                                not null comment '名称',
    sort         int unsigned     default 1                 not null comment '排序',
    create_gmt   datetime         default current_timestamp not null comment '创建时间',
    modified_gmt datetime         default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '职位表';


drop table if exists role;
create table role
(
    id           bigint unsigned                        not null comment 'id' primary key auto_increment,
    code         varchar(32)  default ''                not null comment '编码',
    name         varchar(32)  default ''                not null comment '名称',
    sort         int unsigned default 1                 not null comment '排序',
    create_gmt   datetime     default current_timestamp not null comment '创建时间',
    modified_gmt datetime     default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '角色表';


drop table if exists role_resource;
create table role_resource
(
    id           bigint unsigned                    not null comment 'id' primary key auto_increment,
    role_id      bigint unsigned                    not null comment 'role.id',
    resource_id  bigint unsigned                    not null comment 'resource.id',
    create_gmt   datetime default current_timestamp not null comment '创建时间',
    modified_gmt datetime default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '角色资源关系表';


drop table if exists department;
create table department
(
    id              bigint unsigned                            not null comment 'id' primary key auto_increment,
    name            varchar(32)                                not null comment '名称',
    sort            int unsigned     default 1                 not null comment '排序',
    top_status_code tinyint unsigned default 1                 not null comment '是否顶级. 0: 不是, 1: 是',
    create_gmt      datetime         default current_timestamp not null comment '创建时间',
    modified_gmt    datetime         default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '部门表';


drop table if exists department_relation;
create table department_relation
(
    id           bigint unsigned                    not null comment 'id' primary key auto_increment,
    parent_id    bigint unsigned                    not null comment '父节点 id',
    child_id     bigint unsigned                    not null comment '子节点 id',
    depth        int unsigned                       not null comment '深度',
    create_gmt   datetime default current_timestamp not null comment '创建时间',
    modified_gmt datetime default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '部门关系表';


drop table if exists employee;
create table employee
(
    id                  bigint unsigned                            not null comment 'id' primary key auto_increment,
    account_status_code tinyint unsigned default 1                 not null comment '帐号状态编码. 1: 启用, 2: 禁用',
    status_code         tinyint unsigned default 1                 not null comment '状态编码. 1: 在职, 2: 离职',
    name                varchar(10)                                not null comment '姓名',
    password            varchar(64)                                not null comment '密码',
    phone_number        char(11)                                   not null comment '手机号码',
    avatar_img_url      varchar(128)     default ''                not null comment '头像',
    job_number          varchar(16)      default ''                not null comment '工号',
    email               varchar(64)      default ''                not null comment '邮箱',
    gender_code         tinyint unsigned default 2                 not null comment '性别编码. 0: 女, 1: 男, 2: 保密',
    create_gmt          datetime         default current_timestamp not null comment '创建时间',
    modified_gmt        datetime         default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '员工表';


drop table if exists employee_role;
create table employee_role
(
    id           bigint unsigned                    not null comment 'id' primary key auto_increment,
    employee_id  bigint unsigned                    not null comment 'employee.id',
    role_id      bigint unsigned                    not null comment 'role.id',
    create_gmt   datetime default current_timestamp not null comment '创建时间',
    modified_gmt datetime default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '员工角色关系表';


drop table if exists employee_department;
create table employee_department
(
    id            bigint unsigned                    not null comment 'id' primary key auto_increment,
    employee_id   bigint unsigned                    not null comment 'employee.id',
    department_id bigint unsigned                    not null comment 'department.id',
    create_gmt    datetime default current_timestamp not null comment '创建时间',
    modified_gmt  datetime default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '员工部门关系表';


drop table if exists employee_position;
create table employee_position
(
    id           bigint unsigned                    not null comment 'id' primary key auto_increment,
    employee_id  bigint unsigned                    not null comment 'employee.id',
    position_id  bigint unsigned                    not null comment 'position.id',
    create_gmt   datetime default current_timestamp not null comment '创建时间',
    modified_gmt datetime default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '员工职位关系表';


drop table if exists login_log;
create table login_log
(
    id                      bigint unsigned                           not null comment 'id' primary key auto_increment,
    login_user_id           bigint unsigned default 0                 not null comment '登陆人 id',
    login_user_name         varchar(32)     default ''                not null comment '登陆人姓名',
    login_user_phone_number char(11)                                  not null comment '登陆人手机号码',
    ip                      varchar(15)                               not null comment 'ip',
    location                varchar(64)     default ''                not null comment '地理位置',
    user_agent              varchar(1000)   default ''                not null comment 'user-agent',
    os                      varchar(32)     default ''                not null comment '操作系统',
    browser                 varchar(64)     default ''                not null comment '浏览器',
    status_code             tinyint unsigned                          not null comment '状态编码. 1: 成功, 2: 失败',
    status                  varchar(10)                               not null comment '状态',
    error_msg               varchar(200)    default ''                not null comment '错误信息',
    create_gmt              datetime        default current_timestamp not null comment '创建时间',
    modified_gmt            datetime        default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '登录日志表';


drop table if exists operation_log;
create table operation_log
(
    id                    bigint unsigned                         not null comment 'id' primary key auto_increment,
    operator_id           bigint unsigned                         not null comment '操作人 id',
    operator_name         varchar(32)                             not null comment '操作人姓名',
    operator_phone_number char(11)                                not null comment '操作人手机号码',
    module                varchar(200)                            not null comment '操作模块',
    type                  varchar(32)                             not null comment '操作类型',
    status_code           tinyint unsigned                        not null comment '状态编码. 1: 成功, 2: 失败',
    status                varchar(10)                             not null comment '状态',
    error_msg             varchar(200)  default ''                not null comment '错误信息',
    ip                    varchar(15)                             not null comment 'ip',
    location              varchar(64)   default ''                not null comment '地理位置',
    os                    varchar(32)   default ''                not null comment '操作系统',
    browser               varchar(64)   default ''                not null comment '浏览器',
    request_method        varchar(6)                              not null comment '请求方式',
    request_url           varchar(1000)                           not null comment '请求地址',
    request_content_type  varchar(255)   default ''               not null comment '请求 content-type',
    request_user_agent    varchar(1000) default ''                not null comment '请求 user-agent',
    request_param         text                                    not null comment '请求参数',
    response_result       text                                    not null comment '响应结果',
    wait_time             int           default 0                 not null comment '耗时. 单位: 毫秒',
    create_gmt            datetime      default current_timestamp not null comment '创建时间',
    modified_gmt          datetime      default current_timestamp not null comment '修改时间' on update current_timestamp
) comment '操作日志表';
