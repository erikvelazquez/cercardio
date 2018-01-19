(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('EthnicGroupController', EthnicGroupController);

    EthnicGroupController.$inject = ['EthnicGroup', 'EthnicGroupSearch'];

    function EthnicGroupController(EthnicGroup, EthnicGroupSearch) {

        var vm = this;

        vm.ethnicGroups = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            EthnicGroup.query(function(result) {
                vm.ethnicGroups = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EthnicGroupSearch.query({query: vm.searchQuery}, function(result) {
                vm.ethnicGroups = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
