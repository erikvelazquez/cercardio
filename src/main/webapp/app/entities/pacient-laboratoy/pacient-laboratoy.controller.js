(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientLaboratoyController', PacientLaboratoyController);

    PacientLaboratoyController.$inject = ['PacientLaboratoy', 'PacientLaboratoySearch'];

    function PacientLaboratoyController(PacientLaboratoy, PacientLaboratoySearch) {

        var vm = this;

        vm.pacientLaboratoys = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientLaboratoy.query(function(result) {
                vm.pacientLaboratoys = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientLaboratoySearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientLaboratoys = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
