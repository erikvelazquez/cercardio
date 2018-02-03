(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Indigenous_LanguagesController', Indigenous_LanguagesController);

    Indigenous_LanguagesController.$inject = ['Indigenous_Languages', 'Indigenous_LanguagesSearch'];

    function Indigenous_LanguagesController(Indigenous_Languages, Indigenous_LanguagesSearch) {

        var vm = this;

        vm.indigenous_Languages = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Indigenous_Languages.query(function(result) {
                vm.indigenous_Languages = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Indigenous_LanguagesSearch.query({query: vm.searchQuery}, function(result) {
                vm.indigenous_Languages = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
