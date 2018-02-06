(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ProgramsController', ProgramsController);

    ProgramsController.$inject = ['Programs', 'ProgramsSearch'];

    function ProgramsController(Programs, ProgramsSearch) {

        var vm = this;

        vm.programs = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Programs.query(function(result) {
                vm.programs = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProgramsSearch.query({query: vm.searchQuery}, function(result) {
                vm.programs = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
