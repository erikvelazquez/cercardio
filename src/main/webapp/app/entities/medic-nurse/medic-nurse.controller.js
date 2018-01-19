(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicNurseController', MedicNurseController);

    MedicNurseController.$inject = ['MedicNurse', 'MedicNurseSearch'];

    function MedicNurseController(MedicNurse, MedicNurseSearch) {

        var vm = this;

        vm.medicNurses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            MedicNurse.query(function(result) {
                vm.medicNurses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MedicNurseSearch.query({query: vm.searchQuery}, function(result) {
                vm.medicNurses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
