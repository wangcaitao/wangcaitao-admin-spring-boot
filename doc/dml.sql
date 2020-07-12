truncate table dict;
insert into dict (parent_code, code, name, sort) value ('', 'move-type', '移动类型', 1);
insert into dict (parent_code, code, name, sort) value ('move-type', '1', '上移', 1);
insert into dict (parent_code, code, name, sort) value ('move-type', '2', '下移', 2);
insert into dict (parent_code, code, name, sort) value ('', 'request-method', '请求方法', 1);
insert into dict (parent_code, code, name, sort) value ('request-method', 'GET', 'GET', 1);
insert into dict (parent_code, code, name, sort) value ('request-method', 'POST', 'POST', 2);
insert into dict (parent_code, code, name, sort) value ('request-method', 'PUT', 'PUT', 3);
insert into dict (parent_code, code, name, sort) value ('request-method', 'DELETE', 'DELETE', 4);
insert into dict (parent_code, code, name, sort) value ('', 'resource-status', '资源状态', 1);
insert into dict (parent_code, code, name, sort) value ('resource-status', '1', '启用', 1);
insert into dict (parent_code, code, name, sort) value ('resource-status', '2', '禁用', 2);
insert into dict (parent_code, code, name, sort) value ('', 'resource-type', '资源类型', 1);
insert into dict (parent_code, code, name, sort) value ('resource-type', '1', '菜单', 1);
insert into dict (parent_code, code, name, sort) value ('resource-type', '2', '按钮', 2);
insert into dict (parent_code, code, name, sort) value ('', 'gender', '性别', 1);
insert into dict (parent_code, code, name, sort) value ('gender', '0', '女', 1);
insert into dict (parent_code, code, name, sort) value ('gender', '1', '男', 2);
insert into dict (parent_code, code, name, sort) value ('gender', '2', '保密', 3);
insert into dict (parent_code, code, name, sort) value ('', 'employee-status', '员工状态', 1);
insert into dict (parent_code, code, name, sort) value ('employee-status', '1', '在职', 1);
insert into dict (parent_code, code, name, sort) value ('employee-status', '2', '离职', 2);
insert into dict (parent_code, code, name, sort) value ('', 'account-status', '帐号状态', 1);
insert into dict (parent_code, code, name, sort) value ('account-status', '1', '启用', 1);
insert into dict (parent_code, code, name, sort) value ('account-status', '2', '禁用', 2);
insert into dict (parent_code, code, name, sort) value ('', 'login-status', '登陆状态', 1);
insert into dict (parent_code, code, name, sort) value ('login-status', '1', '成功', 1);
insert into dict (parent_code, code, name, sort) value ('login-status', '2', '失败', 2);
insert into dict (parent_code, code, name, sort) value ('', 'operation-status', '操作状态', 1);
insert into dict (parent_code, code, name, sort) value ('operation-status', '1', '成功', 1);
insert into dict (parent_code, code, name, sort) value ('operation-status', '2', '失败', 2);


truncate table property;
insert into property (name, value, description) value ('default.password', '123456', '初始密码');
insert into property (name, value, description) value ('default.avatar-img-url', '', '默认头像');
insert into property (name, value, description) value ('token.timeout', '10', 'token 失效时间. 单位: 小时');
insert into property (name, value, description) value ('token.single', '1', '一个帐号是否只能一个会话在线. 1: 只能一个, 其他值均认为可以多个');
insert into property (name, value, description) value ('only-view', 'false', '是否只能查看. false: 不是, true: 是');


truncate table resource;
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (1, 1, '系统管理', '', 'GET', 1, 1, 'el-icon-setting', 1);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (2, 1, '员工管理', '/employee', 'GET', 1, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (3, 1, '部门管理', '/department', 'GET', 2, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (4, 1, '角色管理', '/role', 'GET', 3, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (5, 1, '职位管理', '/position', 'GET', 4, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (6, 1, '资源管理', '/resource', 'GET', 5, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (7, 1, '属性管理', '/property', 'GET', 6, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (8, 1, '字典管理', '/dict', 'GET', 7, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (9, 1, '日志管理', '', 'GET', 8, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (10, 1, '登陆日志', '/login-log', 'GET', 1, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (11, 1, '操作日志', '/operation-log', 'GET', 2, 1, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (12, 1, '详情', '/employee/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (13, 1, '分页查询', '/employee', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (14, 1, '保存', '/employee', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (15, 1, '修改', '/employee', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (16, 1, '重置密码', '/employee-password', 'PUT', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (17, 1, '修改帐号状态', '/employee-account-status', 'PUT', 6, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (18, 1, '修改状态', '/employee-status', 'PUT', 7, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (19, 1, '详情', '/department/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (20, 1, '分页查询', '/department', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (21, 1, '保存', '/department', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (22, 1, '修改', '/department', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (23, 1, '删除', '/department/{id}', 'DELETE', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (24, 1, '修改排序', '/department-sort', 'PUT', 6, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (25, 1, '部门树', '/department-tree', 'GET', 7, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (26, 1, '关联员工 - 分页查询', '/department-employee', 'GET', 8, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (27, 1, '关联员工 - 保存', '/department-employee', 'POST', 9, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (28, 1, '关联员工 - 批量删除', '/department-employee', 'DELETE', 10, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (29, 1, '详情', '/role/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (30, 1, '分页查询', '/role', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (31, 1, '保存', '/role', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (32, 1, '修改', '/role', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (33, 1, '删除', '/role/{id}', 'DELETE', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (34, 1, '修改排序', '/role-sort', 'PUT', 6, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (35, 1, '关联员工 - 分页查询', '/role-employee', 'GET', 7, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (36, 1, '关联员工 - 保存', '/role-employee', 'POST', 8, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (37, 1, '关联员工 - 批量删除', '/role-employee', 'DELETE', 9, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (38, 1, '详情', '/position/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (39, 1, '分页查询', '/position', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (40, 1, '保存', '/position', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (41, 1, '修改', '/position', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (42, 1, '删除', '/position/{id}', 'DELETE', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (43, 1, '修改排序', '/position-sort', 'PUT', 6, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (44, 1, '关联员工 - 分页查询', '/position-employee', 'GET', 7, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (45, 1, '关联员工 - 保存', '/position-employee', 'POST', 8, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (46, 1, '关联员工 - 批量删除', '/position-employee', 'DELETE', 9, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (47, 1, '详情', '/resource/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (48, 1, '分页查询', '/resource', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (49, 1, '保存', '/resource', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (50, 1, '修改', '/resource', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (51, 1, '删除', '/resource/{id}', 'DELETE', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (52, 1, '修改排序', '/resource-sort', 'PUT', 6, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (53, 1, '修改状态', '/resource-status', 'PUT', 7, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (54, 1, '资源树', '/resource-tree', 'GET', 8, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (55, 1, '详情', '/property/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (56, 1, '分页查询', '/property', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (57, 1, '保存', '/property', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (58, 1, '修改', '/property', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (59, 1, '删除', '/property/{id}', 'DELETE', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (60, 1, '详情', '/dict/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (61, 1, '分页查询', '/dict', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (62, 1, '保存', '/dict', 'POST', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (63, 1, '修改', '/dict', 'PUT', 4, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (64, 1, '删除', '/dict/{id}', 'DELETE', 5, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (65, 1, '修改排序', '/dict-sort', 'PUT', 6, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (66, 1, '分页查询', '/login-log', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (67, 1, '批量删除', '/login-log', 'DELETE', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (68, 1, '清空', '/login-log-empty', 'DELETE', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (69, 1, '详情', '/operation-log/{id}', 'GET', 1, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (70, 1, '分页查询', '/operation-log', 'GET', 2, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (71, 1, '批量删除', '/operation-log', 'DELETE', 3, 2, '', 0);
insert into resource (id, status_code, name, url, request_method, sort, type_code, icon, top_status_code) values (72, 1, '清空', '/operation-log-empty', 'DELETE', 4, 2, '', 0);


truncate table resource_relation;
insert into resource_relation (parent_id, child_id, depth) values (1, 1, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 2, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 2, 1);
insert into resource_relation (parent_id, child_id, depth) values (3, 3, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 3, 1);
insert into resource_relation (parent_id, child_id, depth) values (4, 4, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 4, 1);
insert into resource_relation (parent_id, child_id, depth) values (5, 5, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 5, 1);
insert into resource_relation (parent_id, child_id, depth) values (6, 6, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 6, 1);
insert into resource_relation (parent_id, child_id, depth) values (7, 7, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 7, 1);
insert into resource_relation (parent_id, child_id, depth) values (8, 8, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 8, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 9, 0);
insert into resource_relation (parent_id, child_id, depth) values (1, 9, 1);
insert into resource_relation (parent_id, child_id, depth) values (10, 10, 0);
insert into resource_relation (parent_id, child_id, depth) values (9, 10, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 10, 2);
insert into resource_relation (parent_id, child_id, depth) values (11, 11, 0);
insert into resource_relation (parent_id, child_id, depth) values (9, 11, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 11, 2);
insert into resource_relation (parent_id, child_id, depth) values (12, 12, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 12, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 12, 2);
insert into resource_relation (parent_id, child_id, depth) values (13, 13, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 13, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 13, 2);
insert into resource_relation (parent_id, child_id, depth) values (14, 14, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 14, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 14, 2);
insert into resource_relation (parent_id, child_id, depth) values (15, 15, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 15, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 15, 2);
insert into resource_relation (parent_id, child_id, depth) values (16, 16, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 16, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 16, 2);
insert into resource_relation (parent_id, child_id, depth) values (17, 17, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 17, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 17, 2);
insert into resource_relation (parent_id, child_id, depth) values (18, 18, 0);
insert into resource_relation (parent_id, child_id, depth) values (2, 18, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 18, 2);
insert into resource_relation (parent_id, child_id, depth) values (19, 19, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 19, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 19, 2);
insert into resource_relation (parent_id, child_id, depth) values (20, 20, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 20, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 20, 2);
insert into resource_relation (parent_id, child_id, depth) values (21, 21, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 21, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 21, 2);
insert into resource_relation (parent_id, child_id, depth) values (22, 22, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 22, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 22, 2);
insert into resource_relation (parent_id, child_id, depth) values (23, 23, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 23, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 23, 2);
insert into resource_relation (parent_id, child_id, depth) values (24, 24, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 24, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 24, 2);
insert into resource_relation (parent_id, child_id, depth) values (25, 25, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 25, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 25, 2);
insert into resource_relation (parent_id, child_id, depth) values (26, 26, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 26, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 26, 2);
insert into resource_relation (parent_id, child_id, depth) values (27, 27, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 27, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 27, 2);
insert into resource_relation (parent_id, child_id, depth) values (28, 28, 0);
insert into resource_relation (parent_id, child_id, depth) values (3, 28, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 28, 2);
insert into resource_relation (parent_id, child_id, depth) values (29, 29, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 29, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 29, 2);
insert into resource_relation (parent_id, child_id, depth) values (30, 30, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 30, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 30, 2);
insert into resource_relation (parent_id, child_id, depth) values (31, 31, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 31, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 31, 2);
insert into resource_relation (parent_id, child_id, depth) values (32, 32, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 32, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 32, 2);
insert into resource_relation (parent_id, child_id, depth) values (33, 33, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 33, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 33, 2);
insert into resource_relation (parent_id, child_id, depth) values (34, 34, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 34, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 34, 2);
insert into resource_relation (parent_id, child_id, depth) values (35, 35, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 35, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 35, 2);
insert into resource_relation (parent_id, child_id, depth) values (36, 36, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 36, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 36, 2);
insert into resource_relation (parent_id, child_id, depth) values (37, 37, 0);
insert into resource_relation (parent_id, child_id, depth) values (4, 37, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 37, 2);
insert into resource_relation (parent_id, child_id, depth) values (38, 38, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 38, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 38, 2);
insert into resource_relation (parent_id, child_id, depth) values (39, 39, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 39, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 39, 2);
insert into resource_relation (parent_id, child_id, depth) values (40, 40, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 40, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 40, 2);
insert into resource_relation (parent_id, child_id, depth) values (41, 41, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 41, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 41, 2);
insert into resource_relation (parent_id, child_id, depth) values (42, 42, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 42, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 42, 2);
insert into resource_relation (parent_id, child_id, depth) values (43, 43, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 43, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 43, 2);
insert into resource_relation (parent_id, child_id, depth) values (44, 44, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 44, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 44, 2);
insert into resource_relation (parent_id, child_id, depth) values (45, 45, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 45, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 45, 2);
insert into resource_relation (parent_id, child_id, depth) values (46, 46, 0);
insert into resource_relation (parent_id, child_id, depth) values (5, 46, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 46, 2);
insert into resource_relation (parent_id, child_id, depth) values (47, 47, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 47, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 47, 2);
insert into resource_relation (parent_id, child_id, depth) values (48, 48, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 48, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 48, 2);
insert into resource_relation (parent_id, child_id, depth) values (49, 49, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 49, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 49, 2);
insert into resource_relation (parent_id, child_id, depth) values (50, 50, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 50, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 50, 2);
insert into resource_relation (parent_id, child_id, depth) values (51, 51, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 51, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 51, 2);
insert into resource_relation (parent_id, child_id, depth) values (52, 52, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 52, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 52, 2);
insert into resource_relation (parent_id, child_id, depth) values (53, 53, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 53, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 53, 2);
insert into resource_relation (parent_id, child_id, depth) values (54, 54, 0);
insert into resource_relation (parent_id, child_id, depth) values (6, 54, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 54, 2);
insert into resource_relation (parent_id, child_id, depth) values (55, 55, 0);
insert into resource_relation (parent_id, child_id, depth) values (7, 55, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 55, 2);
insert into resource_relation (parent_id, child_id, depth) values (56, 56, 0);
insert into resource_relation (parent_id, child_id, depth) values (7, 56, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 56, 2);
insert into resource_relation (parent_id, child_id, depth) values (57, 57, 0);
insert into resource_relation (parent_id, child_id, depth) values (7, 57, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 57, 2);
insert into resource_relation (parent_id, child_id, depth) values (58, 58, 0);
insert into resource_relation (parent_id, child_id, depth) values (7, 58, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 58, 2);
insert into resource_relation (parent_id, child_id, depth) values (59, 59, 0);
insert into resource_relation (parent_id, child_id, depth) values (7, 59, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 59, 2);
insert into resource_relation (parent_id, child_id, depth) values (60, 60, 0);
insert into resource_relation (parent_id, child_id, depth) values (8, 60, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 60, 2);
insert into resource_relation (parent_id, child_id, depth) values (61, 61, 0);
insert into resource_relation (parent_id, child_id, depth) values (8, 61, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 61, 2);
insert into resource_relation (parent_id, child_id, depth) values (62, 62, 0);
insert into resource_relation (parent_id, child_id, depth) values (8, 62, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 62, 2);
insert into resource_relation (parent_id, child_id, depth) values (63, 63, 0);
insert into resource_relation (parent_id, child_id, depth) values (8, 63, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 63, 2);
insert into resource_relation (parent_id, child_id, depth) values (64, 64, 0);
insert into resource_relation (parent_id, child_id, depth) values (8, 64, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 64, 2);
insert into resource_relation (parent_id, child_id, depth) values (65, 65, 0);
insert into resource_relation (parent_id, child_id, depth) values (8, 65, 1);
insert into resource_relation (parent_id, child_id, depth) values (1, 65, 2);
insert into resource_relation (parent_id, child_id, depth) values (66, 66, 0);
insert into resource_relation (parent_id, child_id, depth) values (10, 66, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 66, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 66, 3);
insert into resource_relation (parent_id, child_id, depth) values (67, 67, 0);
insert into resource_relation (parent_id, child_id, depth) values (10, 67, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 67, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 67, 3);
insert into resource_relation (parent_id, child_id, depth) values (68, 68, 0);
insert into resource_relation (parent_id, child_id, depth) values (10, 68, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 68, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 68, 3);
insert into resource_relation (parent_id, child_id, depth) values (69, 69, 0);
insert into resource_relation (parent_id, child_id, depth) values (11, 69, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 69, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 69, 3);
insert into resource_relation (parent_id, child_id, depth) values (70, 70, 0);
insert into resource_relation (parent_id, child_id, depth) values (11, 70, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 70, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 70, 3);
insert into resource_relation (parent_id, child_id, depth) values (71, 71, 0);
insert into resource_relation (parent_id, child_id, depth) values (11, 71, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 71, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 71, 3);
insert into resource_relation (parent_id, child_id, depth) values (72, 72, 0);
insert into resource_relation (parent_id, child_id, depth) values (11, 72, 1);
insert into resource_relation (parent_id, child_id, depth) values (9, 72, 2);
insert into resource_relation (parent_id, child_id, depth) values (1, 72, 3);


truncate table role;
insert into role (id, code, name, sort) value (1, 'SUPER_ADMIN', '超级管理员', 1);
insert into role (id, code, name, sort) value (2, 'MAIN_ADMIN', '主管理员', 2);
insert into role (id, code, name, sort) value (3, 'SUB_ADMIN', '子管理员', 3);


truncate table role_resource;
insert into role_resource (role_id, resource_id) value (1, 12);
insert into role_resource (role_id, resource_id) value (1, 13);
insert into role_resource (role_id, resource_id) value (1, 14);
insert into role_resource (role_id, resource_id) value (1, 15);
insert into role_resource (role_id, resource_id) value (1, 16);
insert into role_resource (role_id, resource_id) value (1, 17);
insert into role_resource (role_id, resource_id) value (1, 18);
insert into role_resource (role_id, resource_id) value (1, 19);
insert into role_resource (role_id, resource_id) value (1, 20);
insert into role_resource (role_id, resource_id) value (1, 21);
insert into role_resource (role_id, resource_id) value (1, 22);
insert into role_resource (role_id, resource_id) value (1, 23);
insert into role_resource (role_id, resource_id) value (1, 24);
insert into role_resource (role_id, resource_id) value (1, 25);
insert into role_resource (role_id, resource_id) value (1, 26);
insert into role_resource (role_id, resource_id) value (1, 27);
insert into role_resource (role_id, resource_id) value (1, 28);
insert into role_resource (role_id, resource_id) value (1, 29);
insert into role_resource (role_id, resource_id) value (1, 30);
insert into role_resource (role_id, resource_id) value (1, 31);
insert into role_resource (role_id, resource_id) value (1, 32);
insert into role_resource (role_id, resource_id) value (1, 33);
insert into role_resource (role_id, resource_id) value (1, 34);
insert into role_resource (role_id, resource_id) value (1, 35);
insert into role_resource (role_id, resource_id) value (1, 36);
insert into role_resource (role_id, resource_id) value (1, 37);
insert into role_resource (role_id, resource_id) value (1, 38);
insert into role_resource (role_id, resource_id) value (1, 39);
insert into role_resource (role_id, resource_id) value (1, 40);
insert into role_resource (role_id, resource_id) value (1, 41);
insert into role_resource (role_id, resource_id) value (1, 42);
insert into role_resource (role_id, resource_id) value (1, 43);
insert into role_resource (role_id, resource_id) value (1, 44);
insert into role_resource (role_id, resource_id) value (1, 45);
insert into role_resource (role_id, resource_id) value (1, 46);
insert into role_resource (role_id, resource_id) value (1, 47);
insert into role_resource (role_id, resource_id) value (1, 48);
insert into role_resource (role_id, resource_id) value (1, 49);
insert into role_resource (role_id, resource_id) value (1, 50);
insert into role_resource (role_id, resource_id) value (1, 51);
insert into role_resource (role_id, resource_id) value (1, 52);
insert into role_resource (role_id, resource_id) value (1, 53);
insert into role_resource (role_id, resource_id) value (1, 54);
insert into role_resource (role_id, resource_id) value (1, 55);
insert into role_resource (role_id, resource_id) value (1, 56);
insert into role_resource (role_id, resource_id) value (1, 57);
insert into role_resource (role_id, resource_id) value (1, 58);
insert into role_resource (role_id, resource_id) value (1, 59);
insert into role_resource (role_id, resource_id) value (1, 60);
insert into role_resource (role_id, resource_id) value (1, 61);
insert into role_resource (role_id, resource_id) value (1, 62);
insert into role_resource (role_id, resource_id) value (1, 63);
insert into role_resource (role_id, resource_id) value (1, 64);
insert into role_resource (role_id, resource_id) value (1, 65);
insert into role_resource (role_id, resource_id) value (1, 66);
insert into role_resource (role_id, resource_id) value (1, 67);
insert into role_resource (role_id, resource_id) value (1, 68);
insert into role_resource (role_id, resource_id) value (1, 69);
insert into role_resource (role_id, resource_id) value (1, 70);
insert into role_resource (role_id, resource_id) value (1, 71);
insert into role_resource (role_id, resource_id) value (1, 72);


truncate table employee;
insert into employee (id, name, password, phone_number) value (1, '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '18888888888');


truncate table employee_role;
insert into employee_role (employee_id, role_id) value (1, 1);
