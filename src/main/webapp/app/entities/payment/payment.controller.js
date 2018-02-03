(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PaymentController', PaymentController);

    PaymentController.$inject = ['Payment', 'PaymentSearch'];

    function PaymentController(Payment, PaymentSearch) {

        var vm = this;

        vm.payments = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Payment.query(function(result) {
                vm.payments = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PaymentSearch.query({query: vm.searchQuery}, function(result) {
                vm.payments = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
