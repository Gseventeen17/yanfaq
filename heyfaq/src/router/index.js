import {createRouter, createWebHashHistory} from 'vue-router'
import First from "@/components/First";
import main from "@/components/Main";
import Rotation from "@/components/Rotation";
import SearchResult from "@/views/SearchResult";
import UserManage from "@/views/UserManage";
import AllQuestions from "@/views/AllQuestions";
import MyQuestions from "@/views/MyQuestions";
import Public from "@/views/Public";
import Person from "@/views/Person";
import Details from "@/views/Details";
import ToBeSolved from "@/views/ToBeSolved";
import Solved from "@/views/Solved";
import TeacherManage from "@/views/TeacherManage";
import CoursesManage from "@/views/CoursesManage";
import EditPassword from "@/views/EditPassword";

const router=new createRouter({
    history: createWebHashHistory(),
    routes:[{
        name:'main',
        path: '/',
        component:First,
        meta:{title:'Minifaq'},
        children:[
            {
                name:'mainview',
                path: '/main',
                component:main,
                redirect:'/allquestions',
                children:[

                    {
                        name:'searchresult',
                        path: '/searchresult',
                        component:SearchResult,
                        meta:{title:'搜索结果'}
                    },
                    {
                        name:'userManage',
                        path: '/userManage',
                        component:UserManage,
                        meta:{title:'用户管理'}
                    },
                    {
                        name:'teacherManage',
                        path: '/teacherManage',
                        component:TeacherManage,
                        meta:{title:'教师管理'}
                    },
                    {
                        name:'coursesManage',
                        path: '/coursesManage',
                        component:CoursesManage,
                        meta:{title:'课程管理'}
                    },
                    {
                        name:'allquestions',
                        path: '/allquestions',
                        component:AllQuestions,
                        meta:{title:'全部问题'},
                    },
                    {
                        name:'myquestions',
                        path: '/myquestions',
                        component:MyQuestions,
                        meta:{title:'我的问题'}
                    },
                    {
                        name:'public',
                        path: '/public',
                        component:Public,
                        meta:{title:'问题发布'}
                    },
                    {
                        name:'person',
                        path: '/person',
                        component:Person,
                        meta:{title:'信息修改'}
                    },
                    {
                        name:'editPassword',
                        path: '/editPassword',
                        component:EditPassword,
                        meta:{title:'密码修改'}
                    },
                    {
                        name:'details',
                        path: '/details',
                        component:Details,
                        meta:{title:'问题详情'}
                    },
                    {
                        name:'tobesolved',
                        path: '/tobesolved',
                        component:ToBeSolved,
                        meta:{title:'待解答'}
                    },
                    {
                        name:'solved',
                        path: '/solved',
                        component:Solved,
                        meta:{title:'已解答'}
                    }
                ]
            },
            {
                name:'index',
                path: '',
                component:Rotation
            }
        ]
    }]
})

export default router
