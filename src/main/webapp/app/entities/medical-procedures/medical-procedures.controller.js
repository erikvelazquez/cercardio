(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medical_ProceduresController', Medical_ProceduresController);

    Medical_ProceduresController.$inject = ['Medical_Procedures', 'Medical_ProceduresSearch'];

    function Medical_ProceduresController(Medical_Procedures, Medical_ProceduresSearch) {

        var vm = this;

        vm.medical_Procedures = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Medical_Procedures.query(function(result) {
                vm.medical_Procedures = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Medical_ProceduresSearch.query({query: vm.searchQuery}, function(result) {
                vm.medical_Procedures = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
