(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('GenderController', GenderController);

    GenderController.$inject = ['Gender', 'GenderSearch'];

    function GenderController(Gender, GenderSearch) {

        var vm = this;

        vm.genders = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Gender.query(function(result) {
                vm.genders = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            GenderSearch.query({query: vm.searchQuery}, function(result) {
                vm.genders = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
