(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('CivilStatusController', CivilStatusController);

    CivilStatusController.$inject = ['CivilStatus', 'CivilStatusSearch'];

    function CivilStatusController(CivilStatus, CivilStatusSearch) {

        var vm = this;

        vm.civilStatuses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CivilStatus.query(function(result) {
                vm.civilStatuses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CivilStatusSearch.query({query: vm.searchQuery}, function(result) {
                vm.civilStatuses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
