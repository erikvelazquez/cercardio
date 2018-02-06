(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DisabilitiesController', DisabilitiesController);

    DisabilitiesController.$inject = ['Disabilities', 'DisabilitiesSearch'];

    function DisabilitiesController(Disabilities, DisabilitiesSearch) {

        var vm = this;

        vm.disabilities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Disabilities.query(function(result) {
                vm.disabilities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DisabilitiesSearch.query({query: vm.searchQuery}, function(result) {
                vm.disabilities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
