(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalDataController', PacientMedicalDataController);

    PacientMedicalDataController.$inject = ['PacientMedicalData', 'PacientMedicalDataSearch'];

    function PacientMedicalDataController(PacientMedicalData, PacientMedicalDataSearch) {

        var vm = this;

        vm.pacientMedicalData = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientMedicalData.query(function(result) {
                vm.pacientMedicalData = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientMedicalDataSearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientMedicalData = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
