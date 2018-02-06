(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicController', MedicController);

    MedicController.$inject = ['Medic', 'MedicSearch'];

    function MedicController(Medic, MedicSearch) {

        var vm = this;

        vm.medics = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Medic.query(function(result) {
                vm.medics = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MedicSearch.query({query: vm.searchQuery}, function(result) {
                vm.medics = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
