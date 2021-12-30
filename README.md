[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6616306&assignment_repo_type=AssignmentRepo)
# jw06

## 报告

重新调整了jw05的结构框架后，用maven工具进行了管理，同时进行了单元测试。

- 主要测试内容是新修改后的的不同物体之间的碰撞逻辑，例如
  - 测试子弹在攻击道具后道具会和子弹一起消失
  - 测试玩家或者怪物在拾取道具后会获得相应的加强效果
  - 不同子弹对于物体和怪物的碰撞结果不同

- 虽然测试样例都能通过，但目前还没能查看代码覆盖度，在执行jacoco的时候会一直报错缺失rules，但到目前还没能解决
 
![avatar](../Junit.png)



- 实现了对地图的存储和读取，以及游戏的暂停和继续
  - 主要思路是通过对地图数组的读写文件操作完成的
