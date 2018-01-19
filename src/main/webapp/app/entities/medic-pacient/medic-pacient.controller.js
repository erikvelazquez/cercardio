(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicPacientController', MedicPacientController);

    MedicPacientController.$inject = ['MedicPacient', 'MedicPacientSearch'];

    function MedicPacientController(MedicPacient, MedicPacientSearch) {

        var vm = this;

        vm.medicPacients = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            MedicPacient.query(function(result) {
                vm.medicPacients = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MedicPacientSearch.query({query: vm.searchQuery}, function(result) {
                vm.medicPacients = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
