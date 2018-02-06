(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicineController', MedicineController);

    MedicineController.$inject = ['Medicine', 'MedicineSearch'];

    function MedicineController(Medicine, MedicineSearch) {

        var vm = this;

        vm.medicines = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Medicine.query(function(result) {
                vm.medicines = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MedicineSearch.query({query: vm.searchQuery}, function(result) {
                vm.medicines = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
