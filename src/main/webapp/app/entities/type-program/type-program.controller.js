(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Type_ProgramController', Type_ProgramController);

    Type_ProgramController.$inject = ['Type_Program', 'Type_ProgramSearch'];

    function Type_ProgramController(Type_Program, Type_ProgramSearch) {

        var vm = this;

        vm.type_Programs = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Type_Program.query(function(result) {
                vm.type_Programs = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Type_ProgramSearch.query({query: vm.searchQuery}, function(result) {
                vm.type_Programs = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
