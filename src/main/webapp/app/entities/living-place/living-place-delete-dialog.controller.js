(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('LivingPlaceDeleteController',LivingPlaceDeleteController);

    LivingPlaceDeleteController.$inject = ['$uibModalInstance', 'entity', 'LivingPlace'];

    function LivingPlaceDeleteController($uibModalInstance, entity, LivingPlace) {
        var vm = this;

        vm.livingPlace = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LivingPlace.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
