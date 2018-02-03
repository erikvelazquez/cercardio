(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientAppController', PacientAppController);

    PacientAppController.$inject = ['PacientApp', 'PacientAppSearch'];

    function PacientAppController(PacientApp, PacientAppSearch) {

        var vm = this;

        vm.pacientApps = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientApp.query(function(result) {
                vm.pacientApps = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientAppSearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientApps = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
