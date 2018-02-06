(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.sidebarVisible = true;
        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.collapseSidebar = collapseSidebar;
        vm.sidebardisplay = "none";
        vm.margin_left = "0";

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function collapseSidebar() {
            vm.sidebarVisible = !vm.sidebarVisible;
            if(vm.sidebarVisible) {
                vm.margin_left = "0";
                vm.sidebardisplay = "none"
            }else {
                vm.margin_left = "20";
                vm.sidebardisplay = "block";
            } 
        }
    }
})();
