(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('NurseController', NurseController);

    NurseController.$inject = ['Nurse', 'NurseSearch'];

    function NurseController(Nurse, NurseSearch) {

        var vm = this;

        vm.nurses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Nurse.query(function(result) {
                vm.nurses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            NurseSearch.query({query: vm.searchQuery}, function(result) {
                vm.nurses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
