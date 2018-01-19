(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DisabilitiesDialogController', DisabilitiesDialogController);

    DisabilitiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Disabilities'];

    function DisabilitiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Disabilities) {
        var vm = this;

        vm.disabilities = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.disabilities.id !== null) {
                Disabilities.update(vm.disabilities, onSaveSuccess, onSaveError);
            } else {
                Disabilities.save(vm.disabilities, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:disabilitiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
