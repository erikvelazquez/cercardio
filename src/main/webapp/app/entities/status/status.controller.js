(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('StatusController', StatusController);

    StatusController.$inject = ['Status', 'StatusSearch'];

    function StatusController(Status, StatusSearch) {

        var vm = this;

        vm.statuses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Status.query(function(result) {
                vm.statuses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            StatusSearch.query({query: vm.searchQuery}, function(result) {
                vm.statuses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
