(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('UserBDController', UserBDController);

    UserBDController.$inject = ['UserBD', 'UserBDSearch'];

    function UserBDController(UserBD, UserBDSearch) {

        var vm = this;

        vm.userBDS = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            UserBD.query(function(result) {
                vm.userBDS = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UserBDSearch.query({query: vm.searchQuery}, function(result) {
                vm.userBDS = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
