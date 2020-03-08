webpackJsonp([2],{"T+/8":function(e,o,s){"use strict";Object.defineProperty(o,"__esModule",{value:!0});var t=s("Dd8w"),n=s.n(t),r=s("NYxO"),a=s("M9A7"),i={name:"Login",data:function(){return{loginForm:{userName:"",password:"",remember:!1},loginRules:{userName:[{required:!0,trigger:"blur",validator:function(e,o,s){o?s():s(new Error("用户名不能为空"))}}],password:[{required:!0,trigger:"blur",validator:function(e,o,s){o?s():s(new Error("密码不能为空"))}}]},passwordType:"password",capsTooltip:!1,loading:!1,showDialog:!1}},created:function(){},mounted:function(){""===this.loginForm.userName?this.$refs.userName.focus():""===this.loginForm.password&&this.$refs.password.focus()},destroyed:function(){},methods:n()({checkCapslock:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},o=e.shiftKey,s=e.key;s&&1===s.length&&(this.capsTooltip=!!(o&&s>="a"&&s<="z"||!o&&s>="A"&&s<="Z")),"CapsLock"===s&&!0===this.capsTooltip&&(this.capsTooltip=!1)},showPwd:function(){var e=this;"password"===this.passwordType?this.passwordType="":this.passwordType="password",this.$nextTick(function(){e.$refs.password.focus()})},handleLogin:function(){var e=this,o=this;this.$refs.loginForm.validate(function(s){if(!s)return!1;e.loading=!0,a.a.login(e.loginForm).then(function(e){e&&1===e.code?(o.$store.dispatch("user/initUserInfo"),o.setUserName(o.loginForm.userName),o.$router.push({path:"/home"})):(o.loading=!1,o.$message({message:e.message,type:"error"}))}).catch(function(e){o.loading=!1})})}},Object(r.c)("user",["setUserName"]),Object(r.c)("user",["clearLogin"]))},l={render:function(){var e=this,o=e.$createElement,s=e._self._c||o;return s("div",{staticClass:"login-container"},[s("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:e.loginForm,rules:e.loginRules,"auto-complete":"on","label-position":"left"}},[s("div",{staticClass:"title-container"},[s("h3",{staticClass:"title"},[e._v("领才教育管理系统")])]),e._v(" "),s("el-form-item",{attrs:{prop:"userName"}},[s("span",{staticClass:"svg-container"},[s("svg-icon",{attrs:{"icon-class":"user"}})],1),e._v(" "),s("el-input",{ref:"userName",attrs:{placeholder:"用户名",name:"userName",type:"text",tabindex:"1","auto-complete":"on"},model:{value:e.loginForm.userName,callback:function(o){e.$set(e.loginForm,"userName",o)},expression:"loginForm.userName"}})],1),e._v(" "),s("el-tooltip",{attrs:{content:"Caps lock is On",placement:"right",manual:""},model:{value:e.capsTooltip,callback:function(o){e.capsTooltip=o},expression:"capsTooltip"}},[s("el-form-item",{attrs:{prop:"password"}},[s("span",{staticClass:"svg-container"},[s("svg-icon",{attrs:{"icon-class":"password"}})],1),e._v(" "),s("el-input",{key:e.passwordType,ref:"password",attrs:{type:e.passwordType,placeholder:"密码",name:"password",tabindex:"2","auto-complete":"on"},on:{blur:function(o){e.capsTooltip=!1}},nativeOn:{keyup:[function(o){return e.checkCapslock(o)},function(o){return!o.type.indexOf("key")&&e._k(o.keyCode,"enter",13,o.key,"Enter")?null:e.handleLogin(o)}]},model:{value:e.loginForm.password,callback:function(o){e.$set(e.loginForm,"password",o)},expression:"loginForm.password"}}),e._v(" "),s("span",{staticClass:"show-pwd",on:{click:e.showPwd}},[s("svg-icon",{attrs:{"icon-class":"password"===e.passwordType?"eye":"eye-open"}})],1)],1)],1),e._v(" "),s("el-checkbox",{staticStyle:{"margin-bottom":"20px","margin-left":"5px"},model:{value:e.loginForm.remember,callback:function(o){e.$set(e.loginForm,"remember",o)},expression:"loginForm.remember"}},[e._v("记住密码")]),e._v(" "),s("el-button",{staticStyle:{width:"100%","margin-bottom":"30px"},attrs:{loading:e.loading,type:"primary"},nativeOn:{click:function(o){return o.preventDefault(),e.handleLogin(o)}}},[e._v("登录")])],1)],1)},staticRenderFns:[]};var c=s("VU/8")(i,l,!1,function(e){s("gA1H"),s("WVdI")},"data-v-668d6d7a",null);o.default=c.exports},WVdI:function(e,o){},gA1H:function(e,o){}});
//# sourceMappingURL=2.95d106b56502ab6c902a.js.map