<template>
  <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" router
           style="min-height: calc(100vh - 100px)"
           >

    <el-menu-item v-show="role==='U'" index="public">
      <el-button color="#7e57c2" round type="primary">
        <el-icon>
          <svg class="icon" height="200" p-id="3337" t="1652342167725"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M766.1 880.9L539.5 769.8c-16.9-8.3-24-28.7-15.7-45.7 4-8.1 11-14.3 19.5-17.3 8.6-2.9 17.9-2.4 26.1 1.6l185.8 91.2 80.2-549.1-361.2 433.4v201.4c0 18.8-15.3 34.1-34.1 34.1S406 904.1 406 885.3V672.7c0-1.8 0.1-3.5 0.4-5.3 0.8-6.6 3.5-12.9 7.7-18L754.4 241 208.8 524.1l128.6 66.7c16.9 8.9 23.6 29.5 15.3 46.6-3.8 7.9-10.7 13.9-19 16.8-8.3 2.9-17.4 2.3-25.3-1.5l-186-96.8c-7.1-3.7-12.7-9.7-15.9-17-4.2-7.9-5.1-17.2-2.5-25.7 2.6-8.6 8.5-15.8 16.5-20l749.6-388.9c5-2.6 10.6-3.9 16.2-3.9 16.7-0.3 31.1 11.5 34.1 27.9 1.1 5.5 0.9 11.1-0.6 16.5L816.1 854.9c-2.2 15.4-14.3 26.8-28.9 29-7.2 1.3-14.6 0.3-21.1-3z m0 0"
                p-id="3338"></path>
          </svg>
        </el-icon>
        发布
      </el-button>
    </el-menu-item>

    <el-menu-item index="allquestions">
      <el-icon>
        <svg class="icon" height="200" p-id="968" t="1652259579939"
             version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
          <path
              d="M886.784 301.8752L660.2752 131.6864a225.28 225.28 0 0 0-271.1552 0L163.84 301.8752A209.92 209.92 0 0 0 80.2816 471.04v272.9984a209.7152 209.7152 0 0 0 209.5104 209.5104h471.04a209.7152 209.7152 0 0 0 208.896-209.5104V471.04a204.8 204.8 0 0 0-82.944-169.1648zM881.8688 471.04v271.5648a118.9888 118.9888 0 0 1-120.2176 120.4224h-471.04a118.9888 118.9888 0 0 1-120.4224-120.4224V471.04a119.1936 119.1936 0 0 1 48.5376-96.8704L444.416 203.5712a134.9632 134.9632 0 0 1 162.6112 0l226.5088 170.1888A121.6512 121.6512 0 0 1 881.8688 471.04z"
              p-id="969"></path>
          <path
              d="M411.2384 496.0256m26.624 0l174.4896 0q26.624 0 26.624 26.624l0 35.84q0 26.624-26.624 26.624l-174.4896 0q-26.624 0-26.624-26.624l0-35.84q0-26.624 26.624-26.624Z"
              p-id="970"></path>
        </svg>
      </el-icon>
      <span>全部</span>
    </el-menu-item>

    <el-sub-menu v-show="role!=='B'" index="2">
      <template #title>
        <el-icon>
          <svg class="icon" height="200" p-id="1129" t="1652259611787"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M513.2288 502.784a212.5824 212.5824 0 1 1 150.1184-61.44 212.1728 212.1728 0 0 1-150.1184 61.44z m0-338.944a127.3856 127.3856 0 1 0 127.3856 127.3856A127.5904 127.5904 0 0 0 513.2288 163.84z m382.1568 763.4944a42.5984 42.5984 0 0 1-42.3936-42.5984 212.5824 212.5824 0 0 0-212.3776-212.1728H385.8432A212.1728 212.1728 0 0 0 173.6704 884.736a42.5984 42.5984 0 1 1-84.992 0 297.1648 297.1648 0 0 1 297.1648-297.1648h254.7712A297.1648 297.1648 0 0 1 937.7792 884.736a42.3936 42.3936 0 0 1-42.3936 42.5984z m0 0"
                p-id="1130"></path>
          </svg>
        </el-icon>
        <span>
                我的
              </span>
      </template>
      <el-menu-item v-show="role==='U'" index="myquestions">
        <el-icon>
          <svg class="icon" height="200" p-id="16404" t="1652261106377"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M245.421 709.579c34.612 34.613 74.928 61.792 119.824 80.781C411.744 810.028 461.119 820 512 820s100.256-9.972 146.755-29.64c44.896-18.99 85.212-46.168 119.824-80.781 34.612-34.612 61.792-74.927 80.781-119.824C879.028 543.256 889 493.881 889 443c0-50.88-9.972-100.256-29.64-146.755-18.989-44.897-46.169-85.212-80.781-119.824-34.612-34.613-74.928-61.792-119.824-80.781C612.256 75.972 562.881 66 512 66s-100.256 9.972-146.755 29.64c-44.896 18.99-85.212 46.168-119.824 80.781-34.612 34.612-61.792 74.927-80.781 119.824C144.972 342.744 135 392.12 135 443c0 50.881 9.972 100.256 29.64 146.755 18.989 44.897 46.169 85.212 80.781 119.824z m45.255-487.903C349.794 162.558 428.395 130 512 130s162.206 32.558 221.324 91.676C792.442 280.793 825 359.395 825 443s-32.558 162.207-91.676 221.324C674.206 723.442 595.605 756 512 756s-162.206-32.558-221.324-91.676C231.558 605.207 199 526.605 199 443s32.558-162.207 91.676-221.324z"
                fill="#1A1A1C" p-id="16405"></path>
            <path d="M384 360m-48 0a48 48 0 1 0 96 0 48 48 0 1 0-96 0Z" fill="#1A1A1C" p-id="16406"></path>
            <path d="M640 360m-48 0a48 48 0 1 0 96 0 48 48 0 1 0-96 0Z" fill="#1A1A1C" p-id="16407"></path>
            <path d="M927 896H97c-17.6 0-32 14.4-32 32s14.4 32 32 32h830c17.6 0 32-14.4 32-32s-14.4-32-32-32z"
                  fill="#1A1A1C" p-id="16408"></path>
          </svg>
        </el-icon>
        我的问题
      </el-menu-item>
      <el-menu-item index="person">
        <el-icon>
          <svg class="icon" height="200" p-id="13361" t="1652260993710"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M745.85770667 245.03751111c3.27793778-13.96849778-1.26634667-28.85745778-12.06158222-38.59342222a40.03157333 40.03157333 0 0 0-6.4978489-4.76387556l-0.01024-0.00568888-254.09422222-149.58592c-16.56604445-9.75075555-37.30318222-6.13717333-49.69358222 7.60149333a40.05432889 40.05432889 0 0 0-4.7616 6.49898667L317.40586667 238.31893333l-0.00455112 0.00682667-0.00341333 0.00682667-271.98008889 461.99921778c-0.24576 0.41642667-0.47217778 0.83968-0.69973333 1.26065777-5.77308445 8.99982222-7.93031111 20.28316445-5.03921778 31.45045333l51.36497778 198.39772445c4.58752 17.71861333 20.42083555 29.53216 37.87548445 29.80067555a39.61173333 39.61173333 0 0 0 20.0135111 0.02048l193.37443556-50.06108444c11.42556445-2.95936 20.39352889-10.59498667 25.39975111-20.32298666 0.12174222-0.19911111 0.25144889-0.39367111 0.37091556-0.59619556l373.31854222-634.14158222a39.78012445 39.78012445 0 0 0 4.46122667-11.10243556z m-439.51217778 593.29763556L158.99306667 876.48028445l-38.16789334-147.42414223 244.95331556-416.09443555 185.54993778 109.23235555-244.98289778 416.14108445z m285.3376-484.69788445l-185.54652444-109.23008 60.97692444-103.58101333 185.5488 109.23235556-60.9792 103.57873777z"
                p-id="13362"></path>
            <path
                d="M520.62208 910.16533333m-55.47690667 0a55.47690667 55.47690667 0 1 0 110.95381334 0 55.47690667 55.47690667 0 1 0-110.95381334 0Z"
                p-id="13363"></path>
            <path
                d="M722.96561778 910.16533333m-55.47690667 0a55.47690667 55.47690667 0 1 0 110.95381334 0 55.47690667 55.47690667 0 1 0-110.95381334 0Z"
                p-id="13364"></path>
            <path
                d="M925.30915555 910.16533333m-55.47690666 0a55.47690667 55.47690667 0 1 0 110.95381333 0 55.47690667 55.47690667 0 1 0-110.95381333 0Z"
                p-id="13365"></path>
          </svg>
        </el-icon>
        信息修改
      </el-menu-item>
      <el-menu-item index="editPassword">
        <el-icon>
          <svg class="icon" height="200" p-id="26262" t="1652261446301"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M365.714286 438.857143v-73.142857a146.285714 146.285714 0 1 1 292.571428 0v73.142857h36.571429a36.571429 36.571429 0 0 1 36.571428 36.571428v219.428572a36.571429 36.571429 0 0 1-36.571428 36.571428H329.142857a36.571429 36.571429 0 0 1-36.571428-36.571428v-219.428572a36.571429 36.571429 0 0 1 36.571428-36.571428h36.571429z m73.142857 0h146.285714v-73.142857a73.142857 73.142857 0 0 0-146.285714 0v73.142857z m-73.142857 73.142857v146.285714h292.571428v-146.285714h-292.571428z m403.090285-260.388571A364.544 364.544 0 0 0 512 146.285714C310.016 146.285714 146.285714 310.016 146.285714 512s163.730286 365.714286 365.714286 365.714286 365.714286-163.730286 365.714286-365.714286h73.142857c0 242.358857-196.498286 438.857143-438.857143 438.857143S73.142857 754.358857 73.142857 512 269.641143 73.142857 512 73.142857c120.283429 0 229.266286 48.384 308.516571 126.756572l49.042286-49.042286 25.856 181.028571-181.028571-25.856 54.418285-54.418285z"
                p-id="26263"></path>
          </svg>
        </el-icon>
        密码修改
      </el-menu-item>

    </el-sub-menu>



    <el-sub-menu v-show="role==='T'" index="3">
      <template #title>
        <el-icon>
          <svg class="icon" height="200" p-id="2095" t="1652261955343"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M916.906667 962.133333a42.666667 42.666667 0 0 1-30.293334-12.8l-149.333333-149.333333a42.666667 42.666667 0 0 1 0-60.16 42.666667 42.666667 0 0 1 60.586667 0l149.333333 149.333333a42.666667 42.666667 0 0 1-30.293333 72.96z"
                fill="#37485d" p-id="2096"></path>
            <path
                d="M528.64 919.466667a89.173333 89.173333 0 0 1-72.533333-37.973334L346.88 725.333333H154.453333a91.306667 91.306667 0 0 1-81.066666-49.066666 89.173333 89.173333 0 0 1 8.106666-93.866667l113.066667-151.893333-60.16-180.053334a89.173333 89.173333 0 0 1 113.493333-113.493333L426.666667 197.546667 582.4 85.333333A89.173333 89.173333 0 0 1 725.333333 156.586667l-2.56 189.44 156.16 112.64a89.6 89.6 0 0 1-25.6 158.293333l-180.906666 56.32-58.026667 183.04a88.32 88.32 0 0 1-71.253333 61.866667 85.333333 85.333333 0 0 1-14.506667 1.28zM343.466667 640a88.746667 88.746667 0 0 1 72.96 37.973333l109.226666 154.453334a4.266667 4.266667 0 0 0 4.266667 0 3.413333 3.413333 0 0 0 2.986667-2.986667l56.32-180.906667a88.746667 88.746667 0 0 1 58.88-58.453333l180.48-56.32a4.266667 4.266667 0 0 0 2.986666-3.413333 2.986667 2.986667 0 0 0 0-3.84l-156.16-107.52a90.026667 90.026667 0 0 1-37.973333-74.24L640 155.733333a3.84 3.84 0 0 0 0-3.84 3.413333 3.413333 0 0 0-4.266667 0l-154.026666 113.493334a89.6 89.6 0 0 1-81.92 13.226666L220.586667 217.6a4.266667 4.266667 0 0 0-4.266667 0 5.12 5.12 0 0 0 0 4.266667L277.333333 401.066667a88.746667 88.746667 0 0 1-13.226666 82.346666L149.76 635.733333a4.693333 4.693333 0 0 0 0 4.266667c0 2.56 2.56 2.133333 3.84 2.56L341.333333 640z"
                fill="#37485d" p-id="2097"></path>
          </svg>
        </el-icon>
        <span>解答</span>
      </template>

      <el-menu-item index="tobesolved">
        <el-icon>
          <svg class="icon" height="200" p-id="4354" t="1652270377465"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M472 1013.92h-130.08A264.64 264.64 0 0 1 77.6 749.6v-411.2A264.64 264.64 0 0 1 341.92 74.08h317.76a264.64 264.64 0 0 1 264.32 264.32v53.6a40 40 0 0 1-80 0v-53.6A184.48 184.48 0 0 0 659.68 154.08H341.92A184.48 184.48 0 0 0 157.6 338.4v411.2a184.48 184.48 0 0 0 184.32 184.32h130.08a40 40 0 1 1 0 80z"
                fill="#333333" p-id="4355"></path>
            <path
                d="M715.84 336.96H454.08a40 40 0 0 1 0-80h261.76a40 40 0 0 1 0 80zM315.04 336.96h-40a40 40 0 0 1 0-80h40a40 40 0 0 1 0 80zM320 565.28h-40a40 40 0 0 1 0-80H320a40 40 0 0 1 0 80zM320 789.28h-40a40 40 0 0 1 0-80H320a40 40 0 0 1 0 80zM720 1008a296 296 0 1 1 296-296A296.32 296.32 0 0 1 720 1008z m0-512a216 216 0 1 0 216 216A216.32 216.32 0 0 0 720 496z"
                fill="#333333" p-id="4356"></path>
            <path d="M720 736a32 32 0 0 1-32-32V544a32 32 0 0 1 64 0v160a32 32 0 0 1-32 32z" fill="#333333"
                  p-id="4357"></path>
            <path d="M872 751.04H720a39.04 39.04 0 0 1 0-77.92h152a39.04 39.04 0 0 1 0 77.92z" fill="#333333"
                  p-id="4358"></path>
          </svg>
        </el-icon>
        待解答
      </el-menu-item>
      <el-menu-item index="solved">
        <el-icon>
          <svg class="icon" height="200" p-id="3773" t="1652270332705"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M621.76 984.96a39.84 39.84 0 0 1-28.32-11.68l-104.8-104.8a40 40 0 0 1 56.64-56.64l77.28 77.28L836 688a40 40 0 1 1 54.88 58.24l-241.6 227.84a39.84 39.84 0 0 1-27.52 10.88z"
                fill="#333333" p-id="3774"></path>
            <path
                d="M325.92 981.92A264.64 264.64 0 0 1 61.6 717.6v-411.2A264.64 264.64 0 0 1 325.92 42.08h317.76a264.64 264.64 0 0 1 264.32 264.32V584a40 40 0 0 1-80 0V306.4A184.48 184.48 0 0 0 643.68 122.08H325.92A184.48 184.48 0 0 0 141.6 306.4v411.2a184.48 184.48 0 0 0 184.32 184.32 40 40 0 0 1 0 80z"
                fill="#333333" p-id="3775"></path>
            <path
                d="M699.84 304.96H438.08a40 40 0 1 1 0-80h261.76a40 40 0 0 1 0 80zM299.04 304.96h-40a40 40 0 0 1 0-80h40a40 40 0 0 1 0 80zM699.84 480.96H438.08a40 40 0 0 1 0-80h261.76a40 40 0 0 1 0 80zM699.84 672.96H438.08a40 40 0 0 1 0-80h261.76a40 40 0 0 1 0 80zM299.04 480.96h-40a40 40 0 0 1 0-80h40a40 40 0 0 1 0 80zM299.04 672.96h-40a40 40 0 0 1 0-80h40a40 40 0 0 1 0 80z"
                fill="#333333" p-id="3776"></path>
          </svg>
        </el-icon>
        已解答
      </el-menu-item>

    </el-sub-menu>

    <el-sub-menu v-show="role==='A'" index="4">
      <template #title>
        <el-icon>
          <!--                <location />-->
          <svg class="icon" height="200" p-id="1432" t="1652261681524"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M362.666667 512h-128A149.333333 149.333333 0 0 1 85.333333 362.666667v-128A149.333333 149.333333 0 0 1 234.666667 85.333333h128A149.333333 149.333333 0 0 1 512 234.666667v128A149.333333 149.333333 0 0 1 362.666667 512z m-128-341.333333A64 64 0 0 0 170.666667 234.666667v128A64 64 0 0 0 234.666667 426.666667h128A64 64 0 0 0 426.666667 362.666667v-128A64 64 0 0 0 362.666667 170.666667zM768 482.56a148.48 148.48 0 0 1-105.386667-42.666667l-48.64-49.493333a148.906667 148.906667 0 0 1 0-211.2l48.64-48.64a149.333333 149.333333 0 0 1 210.773334 0l48.64 48.64a148.906667 148.906667 0 0 1 0 213.333333l-48.64 48.213334a147.626667 147.626667 0 0 1-105.386667 41.813333zM768 170.666667a64 64 0 0 0-45.226667 18.346666L674.133333 239.36a64.426667 64.426667 0 0 0 0 90.453333l48.64 48.64a66.133333 66.133333 0 0 0 90.453334 0l48.64-48.64a64.426667 64.426667 0 0 0 0-90.453333l-48.64-48.64A64 64 0 0 0 768 170.666667zM362.666667 981.333333h-128A149.333333 149.333333 0 0 1 85.333333 832v-128A149.333333 149.333333 0 0 1 234.666667 554.666667h128a149.333333 149.333333 0 0 1 149.333333 149.333333v128A149.333333 149.333333 0 0 1 362.666667 981.333333z m-128-341.333333A64 64 0 0 0 170.666667 704v128A64 64 0 0 0 234.666667 896h128a64 64 0 0 0 64-64v-128A64 64 0 0 0 362.666667 640zM832 981.333333h-128a149.333333 149.333333 0 0 1-149.333333-149.333333v-128a149.333333 149.333333 0 0 1 149.333333-149.333333h128a149.333333 149.333333 0 0 1 149.333333 149.333333v128a149.333333 149.333333 0 0 1-149.333333 149.333333z m-128-341.333333a64 64 0 0 0-64 64v128a64 64 0 0 0 64 64h128a64 64 0 0 0 64-64v-128a64 64 0 0 0-64-64z"
                fill="#37485d" p-id="1433"></path>
          </svg>

        </el-icon>
        <span>管理</span>
      </template>

      <el-menu-item index="userManage">
        <el-icon>
          <svg class="icon" height="200" p-id="1598" t="1652261742203"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M617.386667 896a42.666667 42.666667 0 0 1-42.666667-42.666667v-184.746666a59.306667 59.306667 0 0 0-59.306667-59.306667H209.92a59.306667 59.306667 0 0 0-59.306667 59.306667V853.333333a42.666667 42.666667 0 0 1-85.333333 0v-184.746666a144.64 144.64 0 0 1 144.64-144.64h305.493333a144.64 144.64 0 0 1 144.64 144.64V853.333333a42.666667 42.666667 0 0 1-42.666666 42.666667zM922.026667 896a42.666667 42.666667 0 0 1-42.666667-42.666667v-184.746666a59.306667 59.306667 0 0 0-59.306667-59.306667h-128a42.666667 42.666667 0 1 1 0-85.333333h128a144.64 144.64 0 0 1 144.64 144.64V853.333333a42.666667 42.666667 0 0 1-42.666666 42.666667zM362.666667 491.093333a195.84 195.84 0 1 1 195.413333-195.413333 195.84 195.84 0 0 1-195.413333 195.413333z m0-305.92a110.506667 110.506667 0 1 0 110.08 110.506667A110.506667 110.506667 0 0 0 362.666667 185.173333zM659.2 491.093333a42.666667 42.666667 0 0 1 0-85.333333 110.506667 110.506667 0 0 0 0-220.586667 42.666667 42.666667 0 0 1 0-85.333333 195.84 195.84 0 0 1 0 391.253333z"
                fill="#37485d" p-id="1599"></path>
          </svg>
        </el-icon>
        用户管理
      </el-menu-item>
      <el-menu-item index="teacherManage">
        <el-icon>
          <svg class="icon" height="200" p-id="1764" t="1652261790853"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M682.666667 546.56H341.333333a157.866667 157.866667 0 0 0-157.866666 157.866667v208.213333a42.666667 42.666667 0 0 0 85.333333 0v-208.213333a72.533333 72.533333 0 0 1 72.533333-72.533334h107.946667a351.573333 351.573333 0 0 0-54.613333 138.666667 128 128 0 0 0 256 0 351.573333 351.573333 0 0 0-54.613334-138.666667H682.666667a72.533333 72.533333 0 0 1 72.533333 72.533334v208.213333a42.666667 42.666667 0 0 0 85.333333 0v-208.213333a157.866667 157.866667 0 0 0-157.866666-157.866667zM520.533333 810.666667a42.666667 42.666667 0 0 1-42.666666-42.666667 341.333333 341.333333 0 0 1 42.666666-93.866667 341.333333 341.333333 0 0 1 42.666667 93.866667 42.666667 42.666667 0 0 1-42.666667 42.666667zM512 498.773333a213.333333 213.333333 0 1 0-213.333333-213.333333 213.333333 213.333333 0 0 0 213.333333 213.333333z m0-344.746666a128 128 0 1 1-128 128 128 128 0 0 1 128-128z"
                fill="#37485d" p-id="1765"></path>
          </svg>
        </el-icon>
        教师管理
      </el-menu-item>
      <el-menu-item index="coursesManage">
        <el-icon>
          <svg class="icon" height="200" p-id="1928" t="1652261822813"
               version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
            <path d="M795.733333 469.333333L597.333333 588.373333v198.4l198.4-119.04V469.333333z" fill="#37485d"
                  p-id="1929"></path>
            <path
                d="M925.013333 303.786667a54.613333 54.613333 0 0 0-2.133333-11.52 40.106667 40.106667 0 0 0-17.493333-20.906667l-382.72-221.866667a42.666667 42.666667 0 0 0-42.666667 0L98.133333 270.08a40.106667 40.106667 0 0 0-17.493333 20.906667 54.613333 54.613333 0 0 0-2.133333 11.52 18.346667 18.346667 0 0 0 0 4.693333v401.066667a42.666667 42.666667 0 0 0 20.48 36.693333l384 229.12 6.4 2.56h3.413333A48.64 48.64 0 0 0 512 981.333333h3.413333l6.4-2.56 384-229.12a42.666667 42.666667 0 0 0 20.48-36.693333V308.48a18.346667 18.346667 0 0 0-1.28-4.693333z m-423.68-168.106667l315.306667 182.186667-315.306667 189.013333-315.306666-189.013333zM162.133333 402.773333l298.666667 177.92v282.453334l-298.666667-177.92z m678.4 282.453334l-296.533333 177.92v-282.453334l298.666667-177.92z"
                fill="#37485d" p-id="1930"></path>
          </svg>
        </el-icon>
        课程管理
      </el-menu-item>

    </el-sub-menu>
  </el-menu>
</template>

<script>

export default {
  name: "Aside",
  props:['role','activeIndex'],
}
</script>

<style scoped>

</style>