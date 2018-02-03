(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DiagnosisController', DiagnosisController);

    DiagnosisController.$inject = ['Diagnosis', 'DiagnosisSearch'];

    function DiagnosisController(Diagnosis, DiagnosisSearch) {

        var vm = this;

        vm.diagnoses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Diagnosis.query(function(result) {
                vm.diagnoses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DiagnosisSearch.query({query: vm.searchQuery}, function(result) {
                vm.diagnoses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
